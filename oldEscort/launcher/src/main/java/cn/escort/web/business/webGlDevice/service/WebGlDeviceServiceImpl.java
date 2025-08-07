package cn.escort.web.business.webGlDevice.service;

import cn.escort.web.business.resourceInfo.service.ResourceInfoService;
import cn.escort.web.business.userAgent.domain.BitnessEnum;
import cn.escort.web.business.userAgent.domain.UserAgentInfo;
import cn.escort.web.business.userAgent.service.UserAgentService;
import cn.escort.web.business.webGlDevice.domain.WebGlDevice;
import cn.escort.web.business.webGlDevice.domain.dto.WebGLDeviceWithUaDto;
import cn.escort.web.business.webGlDevice.domain.vo.WebGLDeviceWithUaVo;
import cn.escort.web.business.webGlDevice.repository.WebGlDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebGlDeviceServiceImpl implements WebGlDeviceService {

    private final WebGlDeviceRepository webGlDeviceRepository;

    private final UserAgentService userAgentService;

    private final ResourceInfoService resourceInfoService;



    @Override
    public WebGLDeviceWithUaVo getWebGlDevice(String userAgent) {
        UserAgentInfo userAgentInfo= userAgentService.getUserAgentInfoByUserAgent(userAgent);
        WebGLDeviceWithUaDto webGlDevice = getWebGlDevice(userAgentInfo);
        WebGLDeviceWithUaVo webGLDeviceWithUaVo = new WebGLDeviceWithUaVo();
        BeanUtils.copyProperties(webGlDevice,webGLDeviceWithUaVo);
        return webGLDeviceWithUaVo;
    }

    @Override
    public WebGLDeviceWithUaDto getWebGlDevice(UserAgentInfo userAgentInfo) {

        WebGLDeviceWithUaDto webGLDeviceWithUaDto = new WebGLDeviceWithUaDto();
        WebGlDevice topByOrderByIdDesc = webGlDeviceRepository.findTopByOrderByIdDesc();
        Random random = new Random();
        Long randomIndex = random.nextLong(topByOrderByIdDesc.getId()+10)-10;
        WebGlDevice webGlDevice = webGlDeviceRepository.findTopByIdLessThanOrderByIdDesc(randomIndex);

        webGLDeviceWithUaDto.setVendors(webGlDevice.getVendors());
        webGLDeviceWithUaDto.setRenderer(webGlDevice.getRenderer());
        webGLDeviceWithUaDto.setGpuVendors(webGlDevice.getGpuVendors());
        webGLDeviceWithUaDto.setGpuArchitecture(webGlDevice.getGpuArchitecture());

        int randomNumberWithProbability = resourceInfoService.getCpuCore();
        webGLDeviceWithUaDto.setCpu(randomNumberWithProbability);

        int randMemory = resourceInfoService.getRandMemory(userAgentInfo.getBitness().equals(BitnessEnum.x64));
        webGLDeviceWithUaDto.setMemory(randMemory);
        return webGLDeviceWithUaDto;
    }




}
