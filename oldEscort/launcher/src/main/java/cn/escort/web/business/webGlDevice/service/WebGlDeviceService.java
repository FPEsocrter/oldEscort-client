package cn.escort.web.business.webGlDevice.service;

import cn.escort.web.business.userAgent.domain.UserAgentInfo;
import cn.escort.web.business.webGlDevice.domain.dto.WebGLDeviceWithUaDto;
import cn.escort.web.business.webGlDevice.domain.vo.WebGLDeviceWithUaVo;

public interface WebGlDeviceService {

    WebGLDeviceWithUaVo getWebGlDevice(String userAgent);

    WebGLDeviceWithUaDto getWebGlDevice(UserAgentInfo userAgentInfo);

}
