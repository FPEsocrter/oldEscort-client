package cn.escort.web.business.environment.domain.dto.fingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.LocationEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Schema( description= "地理位置")
@Data
public class LocationDto implements Serializable {

    @NotNull(message = "地理位置选项不能空")
    private LocationEnum type;

    @Schema(description = "经度")
    private Float latitude;

    @Schema(description = "维度")
    private Float longitude;

    @Schema(description = "精度(米)")
    private Float accuracy;

}
