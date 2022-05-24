package com.lzh.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lzh.blog.dao.mapper.SysUserMapper;
import com.lzh.blog.dao.pojo.SysUser;
import com.lzh.blog.service.LoginService;
import com.lzh.blog.service.SysUserService;
import com.lzh.blog.utils.JWTUtils;
import com.lzh.blog.utils.UserThreadLocal;
import com.lzh.blog.vo.ErrorCode;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional//加入事务
public class LoginServiceImpl implements LoginService {
    private static final String slat = "mszlu!@#";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 实现登录功能
     * @param loginParam
     * @return
     */
    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1 检查参数是否合法
         * 2 根据用户名和密码去user表中查询 是否存在
         * 3 如果不存在 登陆失败
         * 4 如果存在，使用jwt 生成token 返回给前端
         * 5 token放入redis中， token：user 信息， 设置过期时间
         * 6 （登陆认真的时候先认证token字符串是否合法，再去redis中认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();

        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password+slat);
        SysUser sysUser = sysUserService.findUser(account,password);
        if(sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());

        }

        //生成token，存入redis
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("token" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }


    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap == null){
            return null;
        }
        //直接跳过redis验证
        // SysUser sysUser = sysUserService.findUserById(1l);

        String s = redisTemplate.opsForValue().get("TOKEN-" + token);
        if(StringUtils.isBlank(s)){
            return null;
        }
        return JSON.parseObject(s,SysUser.class);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN-"+token);

        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.判断参数是否合法
         * 2.判断账户是否存在，存在，返回账户已被注册
         * 3.不存在，注册
         * 4.生成token， 存入redis并返回
         * 5.注意加上事务，一旦中间过程出现任何问题，注册的用户需要回滚，不允许注册
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = this.sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");

        this.sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN-" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
       // return null;
    }


   // UserThreadLocal.put
}

