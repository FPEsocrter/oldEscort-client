package cn.escort.web.business.environment.domain.dto;

import cn.escort.frameworkConfig.web.entity.SelectPage;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema()
@EqualsAndHashCode(callSuper = true)
@Data
public class ListEnvironmentDto extends SelectPage {

    @Schema(description = "环境名称")
    private String name;
    @Schema(description = "环境备注")
    private String remark;
    @Schema(description = "环境区域")
    private String area;

    private WebProxyEnum WebProxy;



}
