package cn.escort.web.business.base.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Data
public class IdsDto {

    @Schema(description = "环境idList")
    @NotNull
    private List<Long> ids;

}
