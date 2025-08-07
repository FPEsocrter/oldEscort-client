package cn.escort.frameworkConfig.web.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public interface JsonEnum {

    int ordinal();
    @JsonValue
    default  int ordinal2(){
        return ordinal();
    };

    /*

      Color.values()[0]
      public static <T extends Enum<T>> T getByOrdinal(Class<T> enumType, int ordinal) {
       T[] values = enumType.getEnumConstants();

     */


}