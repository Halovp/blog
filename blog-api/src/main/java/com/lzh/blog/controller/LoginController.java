package com.lzh.blog.controller;

import com.lzh.blog.service.LoginService;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){

        return  loginService.login(loginParam);
    }
}
