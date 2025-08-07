package cn.escort.web.business.userAgent.domain;

public enum OsEnum {

    WIN7("win7"),
    WIN10("win10")
    ;

    private final String osName;
    OsEnum(String name){
        osName=name;
    }

    public static OsEnum withStr(String osVersion) {
        for (OsEnum osEnum : values()) {
            if (osEnum.osName.equalsIgnoreCase(osVersion)) {
                return osEnum;
            }
        }
        // 如果没有匹配的枚举值，可以选择返回一个默认值或者抛出异常，这里返回null作为示例
        return null;

    }
}
