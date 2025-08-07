package cn.escort.web.business.userAgent.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ArchitectureEnum {

    X86("x86"),

    ;
    private String architectureName;

    ArchitectureEnum(String name){
        this.architectureName=name;
    }

    @JsonValue
    public String getArchitectureName(){
        return this.architectureName;
    }

}
