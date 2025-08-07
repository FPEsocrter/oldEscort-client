package cn.escort.web.business.environment.domain.dto.fingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeOnTypeEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "设备资源信息")
@Data
public class ResourceInfoDto implements Serializable {

    @NotNull(message = "设备资源信息不能空")
    private CustomizeOnTypeEnum type;

    @Schema(description = "2.自定义  必填 ")
    private Integer cpu;

    @Schema(description = "2.自定义  必填 ")
    private Integer memory;
}
