package com.lzh.blog.zelses.daka.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String inChina;
    private String addressProvince;
    private String addressCity;
    private String temperatureStatus;
    private String temperature;
    private String isIll;
    private String closeHb;
    private String closeIll;
    private String healthDetail;
    private String isIsolation;
    private String isolationPlace;
    private String userId;
    private String addressInfo;
    private String isGraduate;
    private String healthStatus;
    private String isIsolate;
    private String isolatePlace;

    @Override
    public String toString() {
        return
                "inChina=" + URLEncoder.encode(inChina)  +"&"+
                "addressProvince=" + URLEncoder.encode(addressProvince)  +"&"+
                "addressCity=" + URLEncoder.encode(addressCity) +"&" +
                "temperatureStatus=" + URLEncoder.encode(temperatureStatus) +"&" +
                "temperature=" + URLEncoder.encode(temperature) +"&" +
                "isIll=" + URLEncoder.encode(isIll) +"&" +
                "closeHb=" + URLEncoder.encode(closeHb) +"&" +
                "closeIll=" + URLEncoder.encode(closeIll) +"&" +
                "healthDetail=" + URLEncoder.encode(healthDetail) +"&" +
                "isIsolation=" + URLEncoder.encode(isIsolation) +"&" +
                "isolationPlace=" + URLEncoder.encode(isolationPlace) +"&" +
                "userId=" + URLEncoder.encode(userId) +"&" +
                "addressInfo=" + URLEncoder.encode(addressInfo) +"&" +
                "isGraduate=" + URLEncoder.encode(isGraduate) +"&" +
                "healthStatus=" + URLEncoder.encode(healthStatus) +"&" +
                "isIsolate=" + URLEncoder.encode(isIsolate) +"&" +
                "isolatePlace=" + URLEncoder.encode(isolatePlace);
    }
}
