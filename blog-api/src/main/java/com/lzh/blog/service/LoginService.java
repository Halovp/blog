package com.lzh.blog.service;

import com.lzh.blog.dao.pojo.SysUser;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.params.LoginParam;

public interface LoginService {

    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 登出
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
