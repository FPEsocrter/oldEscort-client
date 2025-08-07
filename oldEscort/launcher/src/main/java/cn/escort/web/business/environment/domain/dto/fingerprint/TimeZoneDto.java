package cn.escort.web.business.environment.domain.dto.fingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.FollowIpTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Schema( description= "时区")
@Data
public class TimeZoneDto implements Serializable {

    @NotNull(message = "时区选项不能空")
    private FollowIpTypeEnum type;

    @Schema( description= "时区下拉选")
    private String timeZone;

}
