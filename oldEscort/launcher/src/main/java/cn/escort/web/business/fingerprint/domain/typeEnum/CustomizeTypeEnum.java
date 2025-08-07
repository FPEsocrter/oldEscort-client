package cn.escort.web.business.fingerprint.domain.typeEnum;

import cn.escort.frameworkConfig.web.entity.JsonEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description =  "的选项 0.默认" +
                        "1.本机" +
                        "2.自定义或者下拉框",
type = "integer")
public enum CustomizeTypeEnum implements JsonEnum {

    DEFAULT, //0

    TURN_OFF, //1 关闭 或者本机
    CUSTOMIZE,
    ;
}
