package cn.escort.web.business.fingerprint.domain.typeEnum;

import cn.escort.frameworkConfig.web.entity.JsonEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description= "字体选项, 0.(DEFAULT ,默认)," +
        "  1.(TURN_OFFA 本机), " +
        "  2 .(CUSTOMIZE 自定义)," +
        "  3. (BY_UA_ENUM 跟随 ua)" ,
        type ="integer" )
public enum ByUaEnum implements JsonEnum {

    DEFAULT, //0
    TURN_OFF, //1 关闭 或者本机
    CUSTOMIZE, // 自定义
    BY_UA_ENUM, //跟随ua
    ;
}
