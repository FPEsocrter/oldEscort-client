package cn.escort.web.business.environment.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.Map;

@Data
@Schema(description = "环境参数")
public class EnvironmentDto {

    @NotBlank(message = "环境名称不能为空")
    @Schema(description = "环境名称")
    @Length(min = 2,max = 64)
    private String name;

    @Schema( description= "Ua")
    @NotNull(message = "uA不能为空")
    private String userAgent;


    @Schema(description = "环境备注")
    private String remark;

    @Schema(description = "cookie")
    private List<Map<String,String>> cookie;

}
