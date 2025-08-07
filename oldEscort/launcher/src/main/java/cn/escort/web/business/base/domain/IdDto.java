package cn.escort.web.business.base.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class IdDto {

    @Schema(description = "环境id")
    @NotNull
    private Long id;

}
