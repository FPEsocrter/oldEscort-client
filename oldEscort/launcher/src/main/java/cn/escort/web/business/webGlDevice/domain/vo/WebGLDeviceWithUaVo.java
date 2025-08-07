package cn.escort.web.business.webGlDevice.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "随机获取cpu和显卡的信息")
@Data
public class WebGLDeviceWithUaVo {

    private String vendors;

    private String renderer;

    private Integer cpu;

    private Integer memory;
}
