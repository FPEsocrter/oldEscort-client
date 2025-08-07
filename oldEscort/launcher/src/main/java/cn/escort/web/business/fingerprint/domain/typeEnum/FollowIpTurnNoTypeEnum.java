package cn.escort.web.business.fingerprint.domain.typeEnum;

import cn.escort.frameworkConfig.web.entity.JsonEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description= "时区选项, 0.(DEFAULT ,默认)," +
                            "  1.(TURN_OFFA 本机), " +
                            "  2 .(CUSTOMIZE 自定义)," +
                            "  3. (FOLLOW_IP 跟随ip)" ,
            type ="integer" )
public enum FollowIpTurnNoTypeEnum implements JsonEnum {

    DEFAULT, //0
    TURN_OFF, //1 关闭 或者本机
    TURN_ON, // 启动,噪音
    CUSTOMIZE, // 自定义
    FOLLOW_IP, //跟随ip
    ;
}
