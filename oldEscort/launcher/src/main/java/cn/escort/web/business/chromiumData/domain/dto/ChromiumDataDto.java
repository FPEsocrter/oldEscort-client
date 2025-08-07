package cn.escort.web.business.chromiumData.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "chromium需要上传的数据")
@Data
public class ChromiumDataDto {

    @Schema(description = "cookie")
    private List<Map<String,String>> cookie;
}
