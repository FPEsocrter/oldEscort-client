package cn.escort.web.business.environment.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.SqlResultSetMapping;
import lombok.Data;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;

import java.time.LocalDateTime;

@Schema(description = "环境分页数据")
@Data
public class EnvironmentPageVo {

    @Schema(description = "行号")
    private Long rowNumber;

    @Schema(description = "编号")
    private String serialNumber;

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "环境名称")
    private String name;

    @Schema(description = "环境备注")
    private String remark;

    @Schema(description = "环境最后一次打开ip")
    private String lastUseIp;

    @Schema(description = "环境最后一次打开ip的归属地")
    private String area;

    @Schema(description = "环境最后一次打开的时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastOpenTime;

    private WebProxyEnum WebProxy;

    @Schema(description = "当前环境是否打开")
    private Boolean open;


}
