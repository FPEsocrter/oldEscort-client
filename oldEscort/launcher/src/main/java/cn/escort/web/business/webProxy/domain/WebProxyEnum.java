package cn.escort.web.business.webProxy.domain;

import cn.escort.frameworkConfig.web.entity.JsonEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = " 代理类型  0 .(NO_PROXY" +
        "    1.(HTTP)," +
        "    2.(HTTPS)," +
        "    3.(SOCKS)," +
        "    4.(SSH),",
        type = "integer")
public enum WebProxyEnum implements JsonEnum {
    NO_PROXY,
    HTTP,
    HTTPS,
    SOCKS,
    SSH,
    ;
}
