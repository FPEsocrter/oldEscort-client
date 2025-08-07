package cn.escort.web.business.environment.domain.dto.fingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.FollowIpTurnNoTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "字体列表")
@Data
public class FontDto implements Serializable {

    @NotNull(message = "字体选项不能空")
    private FollowIpTurnNoTypeEnum type;

    @Schema(description = "3.自定义 字体列表")
    private List<String> fontList;

}
