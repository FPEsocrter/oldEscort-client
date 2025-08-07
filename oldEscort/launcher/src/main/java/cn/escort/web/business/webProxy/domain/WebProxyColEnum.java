package cn.escort.web.business.webProxy.domain;

import cn.escort.frameworkConfig.web.entity.JsonEnum;

import java.util.Map;

public enum WebProxyColEnum {
    TYPE("type"),

    HOST("host"),

    PORT("port"),

    ACCOUNT("account"),

    PASSWORD("password"),

     OTHER("other"),
    ;

    private String name;
    WebProxyColEnum(String name){
        this.name=name;
    }

   public String getName(){
        return name;
    }
}
