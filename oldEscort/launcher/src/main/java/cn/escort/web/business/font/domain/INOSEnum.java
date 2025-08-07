package cn.escort.web.business.font.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum INOSEnum {

    DARWIN("darwin"),
    MOBILE("mobile"),
    WIN32("win32"),

    ;

    private String osName;

    INOSEnum(String name){
        osName=name;
    }


    @JsonValue
    public String getOsName(){
        return osName;
    }


    public static INOSEnum withStr(String osVersion) {
        for (INOSEnum osEnum : values()) {
            if (osEnum.osName.equalsIgnoreCase(osVersion)) {
                return osEnum;
            }
        }
        // 如果没有匹配的枚举值，可以选择返回一个默认值或者抛出异常，这里返回null作为示例
        return null;
    }


}
