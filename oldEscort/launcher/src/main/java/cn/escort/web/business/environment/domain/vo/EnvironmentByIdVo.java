package cn.escort.web.business.environment.domain.vo;

import cn.escort.web.business.environment.domain.dto.EnvironmentDto;
import cn.escort.web.business.environment.domain.dto.FingerprintDto;
import cn.escort.web.business.environment.domain.dto.WebProxyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnvironmentByIdVo {

    @Schema(description = "环境id")
    private Long id;

    private EnvironmentDto environment;

    private WebProxyDto webProxy;

    private FingerprintDto fingerprint;

}
