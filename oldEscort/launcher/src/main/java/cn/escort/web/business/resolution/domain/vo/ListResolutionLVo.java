package cn.escort.web.business.resolution.domain.vo;

import cn.escort.web.business.resolution.domain.Resolution;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "分辨率")
@Data
public class ListResolutionLVo {

    @Schema(description = "分辨率宽")
    private Integer windowWidth;

    @Schema(description = "分辨率高")
    private Integer windowHeight;


}
