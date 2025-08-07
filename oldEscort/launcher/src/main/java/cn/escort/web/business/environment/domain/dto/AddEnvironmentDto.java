package cn.escort.web.business.environment.domain.dto;

import cn.escort.web.business.chromiumData.domain.dto.ChromiumDataDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema( description= "添加环境需要的参数")
public class AddEnvironmentDto {

    @NotNull(message = "环境信息不能空")
    private EnvironmentDto environment;

    @NotNull(message = "代理信息不能空")
    private WebProxyDto webProxy;

    @NotNull(message = "指纹信息不能空")
    private FingerprintDto fingerprint;


}
