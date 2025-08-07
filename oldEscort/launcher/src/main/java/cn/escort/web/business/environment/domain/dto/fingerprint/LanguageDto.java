package cn.escort.web.business.environment.domain.dto.fingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.FollowIpTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "语言列表")
@Data
public class LanguageDto implements Serializable {

    @NotNull(message = "语言选项不能空")
    private FollowIpTypeEnum type;


    @Schema(description = "自定义,需要上传语言列表")
    private List<String> languages;

}
