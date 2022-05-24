package com.lzh.blog.controller;

import com.lzh.blog.service.LoginService;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        //sso叫做单点登录，如果后期把登录注册功能提出去（单独的服务，提供独立的接口服务）


        return loginService.register(loginParam);
    }
}
