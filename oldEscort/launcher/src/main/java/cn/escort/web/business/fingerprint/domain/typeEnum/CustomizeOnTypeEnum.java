package cn.escort.web.business.fingerprint.domain.typeEnum;

import cn.escort.frameworkConfig.web.entity.JsonEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description =  "的选项 0.默认" +
                        "1.关闭 或者本机" +
                        "2.启动"+
                        "3.自定义或者下拉框",
type = "integer")
public enum CustomizeOnTypeEnum implements JsonEnum {
    DEFAULT, //0
    TURN_OFF, //1 关闭 或者本机
    TURN_ON, // 启动,噪音
    CUSTOMIZE,
    ;
}
