package cn.escort.web.business.userAgent.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PlatformVersionEnum {

    V10_0_0("10.0.0"),
    V6_1("0.1.0"),

    ;
    private String PlatformVersionName;

    PlatformVersionEnum(String name){
        this.PlatformVersionName=name;
    }

    @JsonValue
    public String getPlatformVersionName(){
        return this.PlatformVersionName;
    }
}
