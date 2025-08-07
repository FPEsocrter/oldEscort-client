package cn.escort.web.business.launcher.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OpenFingerprintDto {
    @Schema(description = "环境id")
    @NotNull
    private Long id;

    @Schema(description = "时间戳")
    @NotNull
    private Long timestamp;

}
