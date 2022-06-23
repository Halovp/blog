package com.lzh.blog.zelses.daka.controller;



import com.lzh.blog.zelses.daka.pojo.Student;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Component
class App {


    //执行打卡
    public  void daka() throws IOException {
        //tes();//打卡验证
        URL url = new URL("http://jc.ncu.edu.cn/gate/student/signIn");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Accept", "*/*");
        httpConn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        httpConn.setRequestProperty("Connection", "keep-alive");
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpConn.setRequestProperty("Cookie", "ncu_rygk_work_weixin_token=eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJlY2hpc2FuIiwic3ViIjoiNTgwMTEyMDAxNyIsImlhdCI6MTY1NTg3MDI3OX0.u0UDKVbYL_GVLlSJFH_EvojNLG7Ogr2oZQ4R8aUzAOLTC98AJKNkTpL5ivxTYqrD-6NzeBr_-UK2h2JuBMbp3Q; ncu_rygk_work_weixin_userData={%22id%22:373388%2C%22userId%22:%225801120017%22%2C%22userName%22:%22%E5%88%98%E7%A5%9A%E6%B4%AA%22%2C%22userCode%22:%225801120017%22%2C%22avatar%22:%22gate/image/head/student/undergraduate/65ca83e1-ed98-4265-9e20-550add3dd02e.jpeg%22%2C%22sex%22:%22%E7%94%B7%22%2C%22idCard%22:%22360732200112103619%22%2C%22mobile%22:%2218370700890%22%2C%22email%22:null%2C%22originPlace%22:%22%E6%B1%9F%E8%A5%BF%22%2C%22userType%22:%221%22%2C%22roleId%22:%220005%22%2C%22deptId%22:null%2C%22deptName%22:null%2C%22campus%22:null%2C%22college%22:%229100%22%2C%22collegeName%22:%22%E6%95%B0%E5%AD%A6%E4%B8%8E%E8%AE%A1%E7%AE%97%E6%9C%BA%E5%AD%A6%E9%99%A2%22%2C%22major%22:%2261091%22%2C%22majorName%22:%22%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%B1%BB%22%2C%22grade%22:%222020%22%2C%22aClass%22:%22610912002%22%2C%22className%22:null%2C%22cardId%22:%2200706738100792802603%22%2C%22cardStatus%22:%221%22%2C%22role%22:null}");
        httpConn.setRequestProperty("Origin", "http://jc.ncu.edu.cn");
        httpConn.setRequestProperty("Referer", "http://jc.ncu.edu.cn/?code=mhhooskyDCAzYBdkGwah9T0OD4YFwg13Q6nHGpGKLk0&state=STATE");
        httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
        httpConn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        httpConn.setRequestProperty("token", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJlY2hpc2FuIiwic3ViIjoiNTgwMTEyMDAxNyIsImlhdCI6MTY1NTg3MDI3OX0.u0UDKVbYL_GVLlSJFH_EvojNLG7Ogr2oZQ4R8aUzAOLTC98AJKNkTpL5ivxTYqrD-6NzeBr_-UK2h2JuBMbp3Q");

        httpConn.setDoOutput(true);
        String s1 = "inChina=%E6%98%AF&" +
                "addressProvince=%E6%B1%9F%E8%A5%BF%E7%9C%81&" +
                "addressCity=%E5%8D%97%E6%98%8C%E5%B8%82&" +
                "temperatureStatus=%E6%AD%A3%E5%B8%B8&" +
                "temperature=0&isIll=%E5%90%A6&" +
                "closeHb=%E5%90%A6&closeIll=%E5%90%A6&" +
                "healthDetail=%E6%97%A0%E5%BC%82%E5%B8%B8&" +
                "isIsolation=%E5%90%A6&" +
                "isolationPlace=%E6%97%A0&" +
                "userId=5801120017&" +
                "addressInfo=%E6%B1%9F%E8%A5%BF%E7%9C%81%E5%8D%97%E6%98%8C%E5%A4%A7%E5%AD%A6&" +
                "isGraduate=%E5%90%A6&" +
                "healthStatus=%E6%97%A0%E5%BC%82%E5%B8%B8&" +
                "isIsolate=%E5%90%A6&" +
                "isolatePlace=%E6%97%A0";//标准数据
        //String decode = URLDecoder.decode(s1, "utf-8");
        //s1.replaceFirst();
        Student code = encode.code();
        String s2 = code.toString();
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write(s2);
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        //System.out.println(responseStream);
        System.out.println(response);

    }


    //login by token 打卡验证
    public  void tes() throws IOException {
        URL url = new URL("http://jc.ncu.edu.cn/system/auth/loginByToken");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("Accept", "*/*");
        httpConn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        httpConn.setRequestProperty("Connection", "keep-alive");
        httpConn.setRequestProperty("Cookie", "ncu_rygk_work_weixin_token=eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJlY2hpc2FuIiwic3ViIjoiNTgwMTEyMDAxNyIsImlhdCI6MTY1NTg3MDI3OX0.u0UDKVbYL_GVLlSJFH_EvojNLG7Ogr2oZQ4R8aUzAOLTC98AJKNkTpL5ivxTYqrD-6NzeBr_-UK2h2JuBMbp3Q; ncu_rygk_work_weixin_userData={%22id%22:373388%2C%22userId%22:%225801120017%22%2C%22userName%22:%22%E5%88%98%E7%A5%9A%E6%B4%AA%22%2C%22userCode%22:%225801120017%22%2C%22avatar%22:%22gate/image/head/student/undergraduate/65ca83e1-ed98-4265-9e20-550add3dd02e.jpeg%22%2C%22sex%22:%22%E7%94%B7%22%2C%22idCard%22:%22360732200112103619%22%2C%22mobile%22:%2218370700890%22%2C%22email%22:null%2C%22originPlace%22:%22%E6%B1%9F%E8%A5%BF%22%2C%22userType%22:%221%22%2C%22roleId%22:%220005%22%2C%22deptId%22:null%2C%22deptName%22:null%2C%22campus%22:null%2C%22college%22:%229100%22%2C%22collegeName%22:%22%E6%95%B0%E5%AD%A6%E4%B8%8E%E8%AE%A1%E7%AE%97%E6%9C%BA%E5%AD%A6%E9%99%A2%22%2C%22major%22:%2261091%22%2C%22majorName%22:%22%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%B1%BB%22%2C%22grade%22:%222020%22%2C%22aClass%22:%22610912002%22%2C%22className%22:null%2C%22cardId%22:%2200706738100792802603%22%2C%22cardStatus%22:%221%22%2C%22role%22:null}");
        httpConn.setRequestProperty("Referer", "http://jc.ncu.edu.cn/?code=mhhooskyDCAzYBdkGwah9T0OD4YFwg13Q6nHGpGKLk0&state=STATE");
        httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
        httpConn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        httpConn.setRequestProperty("token", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJlY2hpc2FuIiwic3ViIjoiNTgwMTEyMDAxNyIsImlhdCI6MTY1NTg3MDI3OX0.u0UDKVbYL_GVLlSJFH_EvojNLG7Ogr2oZQ4R8aUzAOLTC98AJKNkTpL5ivxTYqrD-6NzeBr_-UK2h2JuBMbp3Q");

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();

        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        System.out.println(response);

    }







}
