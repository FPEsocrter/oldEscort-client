package cn.escort.web.business.fingerprint.domain.typeEnum;

import cn.escort.frameworkConfig.web.entity.JsonEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description =" 0.默认 ," +
        "  1.本机, " +
        "  2.噪音" +
        " 3.跟随ip," +
        "    ;" ,
type= "integer" )
public enum OnlyFollowIpEnum implements JsonEnum {

    DEFAULT, //0
    TURN_OFF, //1 关闭 或者本机
    TURN_ON, // 随机
    FOLLOW_IP, //跟随ip,
    ;
}
