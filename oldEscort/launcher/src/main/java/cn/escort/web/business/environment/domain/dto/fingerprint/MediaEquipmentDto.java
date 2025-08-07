package cn.escort.web.business.environment.domain.dto.fingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeOnTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "媒体设备")
@Data
public class MediaEquipmentDto implements Serializable {

    @NotNull(message = "媒体设备选项不能空")
    private CustomizeOnTypeEnum type;

    @Schema(description = "2.自定义 麦克风的数量 必填")
    private Integer microphone;

    @Schema(description = "2.自定义 音响的数量 必填")
    private Integer speaker;

    @Schema(description = "2.自定义 摄像头的数量")
    private Integer videoCamera;

}
