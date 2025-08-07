package cn.escort.web.business.environment.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ByIdEnvironmentDto {

    @NotNull(message = "环境信息不能空")
    @Range(message = "输入有效的值的")
    private Long id;

    private Boolean environment;

    private Boolean  webProxy;

    private Boolean fingerprint;

    private Boolean chromiumData;


}
