package com.lzh.blog.zelses.daka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@EnableScheduling
public class RunClock  {


    @Autowired
    private App app;

    @Scheduled(cron = "0 0 9 * * ?")
    public String insert(){
        try {
            app.tes();
            app.daka();
            System.out.println("打卡成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "打卡成功";
    }


}
