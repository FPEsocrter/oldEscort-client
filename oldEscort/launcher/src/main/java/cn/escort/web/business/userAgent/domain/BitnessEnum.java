package cn.escort.web.business.userAgent.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BitnessEnum {

    x32("32"),
    x64("64"),
    ;

    private String BitnessName;
    BitnessEnum(String name){
        this.BitnessName=name;
    }

    @JsonValue
    public String getBitnessName(){
        return this.BitnessName;
    }
}
