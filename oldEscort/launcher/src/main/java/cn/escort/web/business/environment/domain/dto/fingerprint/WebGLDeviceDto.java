package cn.escort.web.business.environment.domain.dto.fingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeOnTypeEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "WebGL元数据")
@Data
public class WebGLDeviceDto implements Serializable {

   @NotNull(message = "WebGL元数据选项不能空")
   private CustomizeOnTypeEnum type;

   @Schema(description = "2.自定义WebGL厂商 ")
   private String vendors;

   @Schema(description = "2.自定义 WebGL渲染")
   private String renderer;

}
