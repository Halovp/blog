package com.lzh.blog;

import com.alibaba.fastjson.JSON;
import com.lzh.blog.dao.mapper.TagMapper;
import com.lzh.blog.dao.pojo.SysUser;
import com.lzh.blog.dao.pojo.Tag;
import com.lzh.blog.utils.JWTUtils;
import com.lzh.blog.vo.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class blogtest {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void po(){
        SysUser sysUser = new SysUser();
        String token = JWTUtils.createToken(12l);
        //redisTemplate.opsForValue().set("token" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        System.out.println("token"+token);
        //return tagMapper.findTagsByArticleId(1L);
    }
}
