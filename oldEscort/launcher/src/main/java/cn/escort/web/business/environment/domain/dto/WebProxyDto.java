package cn.escort.web.business.environment.domain.dto;

import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
@Schema(description = "代理参数")
public class WebProxyDto {

    @NotNull(message = "代理类型不能空")
    private WebProxyEnum type;

    @Schema(description = "代理主机 不是类型0 必填")
    private String host;

    @Schema(description = "代理端口 不是类型0 必填")
    private Integer port;

    @Schema(description = "代理账号")
    private String account;

    @Schema(description = "代理密码")
    private String password;

    @Schema(description = "其他信息,暂时没有")
    private Map<String,String> other;

}
