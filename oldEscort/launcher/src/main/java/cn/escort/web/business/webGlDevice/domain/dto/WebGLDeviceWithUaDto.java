package cn.escort.web.business.webGlDevice.domain.dto;

import lombok.Data;

@Data
public class WebGLDeviceWithUaDto {
    private String vendors;

    private String renderer;

    private String gpuVendors;

    private String gpuArchitecture;

    private Integer cpu;

    private Integer memory;
}
