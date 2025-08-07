package cn.escort.web.business.userAgent.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PlatformEnum {

    WINDOWS("Windows"),
    ANDROID("Android"),


    ;

    private String platformName;

    PlatformEnum(String name){
        this.platformName=name;
    }


    @JsonValue
    public String getPlatformName(){
        return this.platformName;
    }
}
