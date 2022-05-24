package com.lzh.blog.controller;

import com.lzh.blog.service.LoginService;
import com.lzh.blog.vo.Result;
import com.lzh.blog.service.LoginService;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;


    /**
     * 实现退出登陆的功能
     * @param token
     * @return
     */
    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
