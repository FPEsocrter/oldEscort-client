package cn.escort.web.business.fingerprint.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAgentDto {

    @Schema(description = "ua")
    @NotBlank(message = "userAgent不能空")
    private String userAgent;

}
