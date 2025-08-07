package cn.escort.web.business.fingerprint.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UaByVersionDto {

    @Schema(description = "主要的版本信息")
    @NotEmpty(message = "主要版本不能空")
    private List<Integer> uaVersion;



}
