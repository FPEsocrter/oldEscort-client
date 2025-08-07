package cn.escort.web.business.environment.domain.dto.fingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "端口扫描保护")
@Data
public class OpenPortDto implements Serializable {

    @NotNull(message = "端口扫描保护选项不能空")
    private CustomizeTypeEnum type;

    @Schema(description = "2.自定义 开端的端口 必填")
    private List<Integer> list;

}
