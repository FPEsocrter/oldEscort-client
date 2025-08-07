package cn.escort.web.business.environment.domain.dto;

import cn.escort.web.business.chromiumData.domain.dto.ChromiumDataDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModifyEnvironmentDto {

    @Schema(description = "环境id")
    @NotNull
    private Long id;

    @Valid
    private EnvironmentDto environment;

    @Valid
    private WebProxyDto webProxy;

    @Valid
    private FingerprintDto fingerprint;


}
