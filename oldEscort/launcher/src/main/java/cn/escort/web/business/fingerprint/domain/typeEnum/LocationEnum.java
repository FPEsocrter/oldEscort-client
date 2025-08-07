package cn.escort.web.business.fingerprint.domain.typeEnum;

import cn.escort.frameworkConfig.web.entity.JsonEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description= "  0.默认" +
                    " 1.本机" +
                    " 2.访问自定义" +
                    " 3.访问基于ip" +
                    " 4.直接允许 自定义" +
                    " 5.直接允许 访问基于ip",
type = "integer")
public enum LocationEnum implements JsonEnum {
    DEFAULT, //0
    TURN_OFF, //1 关闭=使用本机
    CALL_CUSTOMIZE,// 访问自定义
    CALL_FOLLOW_IP, //访问基于ip
    ALLOW_CUSTOMIZE, // 直接允许 自定义
    ALLOW_FOLLOW_IP,  //直接允许 访问基于ip
    ;
}
