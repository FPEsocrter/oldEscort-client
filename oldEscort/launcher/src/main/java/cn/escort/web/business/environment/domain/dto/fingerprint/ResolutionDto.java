package cn.escort.web.business.environment.domain.dto.fingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "分辨率")
@Data
public class ResolutionDto implements Serializable {

    @NotNull(message = "分辨率选项不能空")
    private CustomizeTypeEnum type;

    @Schema(description = "3.自定义 分辨率宽")
    private Integer windowWidth;
    @Schema(description = "3.自定义 分辨率高")
    private Integer windowHeight;


}
