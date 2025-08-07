package cn.escort.web.business.fingerprint.service;

import cn.escort.frameworkConfig.web.entity.DeletedEnum;
import cn.escort.web.business.canvas.service.CanvasService;
import cn.escort.web.business.environment.domain.dto.FingerprintDto;
import cn.escort.web.business.environment.domain.dto.fingerprint.MediaEquipmentDto;
import cn.escort.web.business.fingerprint.domain.Fingerprint;
import cn.escort.web.business.fingerprint.domain.OpenFingerprint;
import cn.escort.web.business.fingerprint.domain.SimpleFingerprint;
import cn.escort.web.business.fingerprint.domain.openFingerprint.*;
import cn.escort.web.business.fingerprint.domain.typeEnum.*;
import cn.escort.web.business.font.service.FontService;
import cn.escort.web.business.mediaEquipment.service.MediaEquipmentService;
import cn.escort.web.business.speechVoices.service.SpeechVoiceCountryService;
import cn.escort.web.business.userAgent.domain.OsEnum;
import cn.escort.web.business.userAgent.domain.UserAgentInfo;
import cn.escort.web.business.userAgent.service.UserAgentService;
import cn.escort.web.business.webGlDevice.domain.WebGlDevice;
import cn.escort.web.business.webGlDevice.domain.dto.WebGLDeviceWithUaDto;
import cn.escort.web.business.webGlDevice.repository.WebGlDeviceRepository;
import cn.escort.web.business.webGlDevice.service.WebGlDeviceService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FingerprintServiceImpl implements FingerprintService {

    private final FontService fontService;

    private final CanvasService canvasService;

    private final WebGlDeviceService webGlDeviceService;

    private final WebGlDeviceRepository webGlDeviceRepository;

    private final SpeechVoiceCountryService speechVoiceCountryService;

    private final MediaEquipmentService mediaEquipmentService;

    private final UserAgentService userAgentService;

    public Fingerprint addConvert(Long id, FingerprintDto dto, String userAgent) {
        Fingerprint fingerprint = new Fingerprint();
        fingerprint.setEnvironmentId(id);
        fingerprint.setDeleted(DeletedEnum.NOT_DELETED);
        SimpleFingerprint simpleFingerprint = new SimpleFingerprint();
        BeanUtils.copyProperties(dto, simpleFingerprint);
        simpleFingerprint.setUserAgent(userAgent);
        fingerprint.setAddFingerprint(simpleFingerprint);
        OpenFingerprint openFingerprint = dtoToOpenFingerprint(simpleFingerprint, null, Boolean.TRUE);
        fingerprint.setOpenFingerprint(openFingerprint);


        return fingerprint;
    }

    @Override
    public void updateConvert(FingerprintDto dto, String userAgent, Fingerprint fingerprint) {

        SimpleFingerprint simpleFingerprint = fingerprint.getAddFingerprint();
        if (Objects.nonNull(dto)) {
            BeanUtils.copyProperties(dto, simpleFingerprint);
        }

        if (StringUtils.isNotBlank(userAgent)) {
            simpleFingerprint.setUserAgent(userAgent);
        }
        fingerprint.setAddFingerprint(simpleFingerprint);

        OpenFingerprint openFingerprint = dtoToOpenFingerprint(simpleFingerprint, fingerprint, dto.getNewEvn());
        fingerprint.setOpenFingerprint(openFingerprint);

    }

    @Override
    public OpenFingerprint SimpleFingerprintToOpenFingerprint(SimpleFingerprint simpleFingerprint) {
        return dtoToOpenFingerprint(simpleFingerprint, null, Boolean.TRUE);
    }

    private OpenFingerprint dtoToOpenFingerprint(SimpleFingerprint newf, Fingerprint fingerprint, Boolean newEnv) {
        SimpleFingerprint oldF = null;
        OpenFingerprint openFingerprint = null;
        if (Objects.isNull(fingerprint) || newEnv) {
            newEnv = Boolean.TRUE;
            openFingerprint = new OpenFingerprint();
        } else {
            newEnv = Boolean.FALSE;
            oldF = fingerprint.getAddFingerprint();
            openFingerprint = fingerprint.getOpenFingerprint();
        }

        openFingerprint.setInit(SimpleTypeEnum.TURN_ON);

        UserAgentInfo userAgentInfo = userAgentService.getUserAgentInfoByUserAgent(newf.getUserAgent());
        userAgent:
        {

            if (newEnv || !newf.getUserAgent().equals(oldF.getUserAgent())) {

                OpenUserAgent userAgent = new OpenUserAgent();
                userAgent.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                userAgent.setUserAgent(userAgentInfo.getShowUserAgent());
                openFingerprint.setUa(userAgent);


                UserAgentMetadata:
                {
                    OpenUserAgentMetadata userAgentMetadata = userAgentService.getOpenUserAgentMetadata(userAgentInfo);
                    userAgentMetadata.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                    openFingerprint.setUaMetadata(userAgentMetadata);
                }
            }

        }

        timeZone:
        {
            if (newEnv || !newf.getTimeZone().equals(oldF.getTimeZone())) {

                OpenTimeZone timeZone = new OpenTimeZone();
                switch (newf.getTimeZone().getType()) {
                    case DEFAULT, FOLLOW_IP -> timeZone.setType(OpenCustomizeTypeEnum.FOLLOW_IP);
                    case TURN_OFF -> timeZone.setType(OpenCustomizeTypeEnum.TURN_OFF);
                    case CUSTOMIZE -> {
                        timeZone.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        timeZone.setGmt(newf.getTimeZone().getTimeZone());
                    }
                }
                openFingerprint.setTimeZone(timeZone);

            }

        }

        webRTC:
        {
            if (newEnv || !newf.getWebRTC().equals(oldF.getWebRTC())) {

                OpenWebRTC webRTC = new OpenWebRTC();
                switch (newf.getWebRTC()) {
                    case DEFAULT, ONLY_TCP -> webRTC.setType(OpenCustomizeTypeEnum.CUSTOMIZE1);
                    case FOLLOW_IP -> webRTC.setType(OpenCustomizeTypeEnum.FOLLOW_IP);
                    case TURN_OFF -> webRTC.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                webRTC.setPrivateIp(generateRandomIpAddress());
                openFingerprint.setWebRTC(webRTC);
            }
        }
        location:
        {
            if (newEnv || !newf.getLocation().equals(oldF.getLocation())) {

                OpenLocation location = new OpenLocation();
                switch (newf.getLocation().getType()) {
                    case ALLOW_FOLLOW_IP:
                        location.setPermissions(Boolean.TRUE);
                    case DEFAULT:
                    case CALL_FOLLOW_IP:
                        location.setType(OpenCustomizeTypeEnum.FOLLOW_IP);
                        break;
                    case TURN_OFF:
                        location.setType(OpenCustomizeTypeEnum.TURN_OFF);
                        break;
                    case ALLOW_CUSTOMIZE:
                        location.setPermissions(Boolean.TRUE);
                    case CALL_CUSTOMIZE:
                        location.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        location.setLatitude(newf.getLocation().getLatitude());
                        location.setAccuracy(newf.getLocation().getAccuracy());
                        location.setLongitude(newf.getLocation().getLongitude());
                }
                openFingerprint.setLocation(location);
            }
        }

        language:
        {
            if (newEnv || !newf.getLanguage().equals(oldF.getLanguage())) {

                OpenLanguage language = new OpenLanguage();
                switch (newf.getLanguage().getType()) {
                    case DEFAULT, FOLLOW_IP -> language.setType(OpenCustomizeTypeEnum.FOLLOW_IP);
                    case TURN_OFF -> language.setType(OpenCustomizeTypeEnum.TURN_OFF);
                    case CUSTOMIZE -> {
                        language.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        language.setLanguages(newf.getLanguage().getLanguages());
                    }
                }
                openFingerprint.setLanguage(language);
            }
        }
        resolution:
        {
            if (newEnv || !newf.getResolution().equals(oldF.getResolution())) {
                OpenResolution resolution = new OpenResolution();
                switch (newf.getResolution().getType()) {
                    case DEFAULT, TURN_OFF -> resolution.setType(OpenCustomizeTypeEnum.TURN_OFF);
                    case CUSTOMIZE -> {
                        resolution.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        resolution.setWindowHeight(newf.getResolution().getWindowHeight());
                        resolution.setWindowHeight(newf.getResolution().getWindowWidth() - 40);
                        resolution.setMonitorHeight(newf.getResolution().getWindowHeight());
                        resolution.setMonitorWidth(newf.getResolution().getWindowWidth());
                    }
                }
                openFingerprint.setResolution(resolution);
            }
        }


        font:
        {
            if (newEnv || !newf.getFont().equals(oldF.getFont())) {
                OpenFont font = new OpenFont();
                switch (newf.getFont().getType()) {
                    case DEFAULT, FOLLOW_IP -> {
                        font.setType(OpenCustomizeTypeEnum.FOLLOW_IP);
                    }
                    case TURN_ON -> {
                        font.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        List<String> randFontList = fontService.getRandFontList(userAgentInfo);
                        font.setFontMap(fontService.getFontMap(randFontList));
                    }
                    case CUSTOMIZE -> {
                        font.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        font.setFontMap(fontService.getFontMap(newf.getFont().getFontList()));
                    }
                    case TURN_OFF -> font.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                openFingerprint.setFont(font);

            }

        }

        canvas:
        {
            if (newEnv || !newf.getCanvas().equals(oldF.getCanvas())) {
                OpenCanvas canvas = new OpenCanvas();
                switch (newf.getCanvas()) {
                    case DEFAULT, TURN_ON -> {
                        canvas.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        canvas.setColoredPointList(canvasService.getRandCanvas());
                    }
                    case TURN_OFF -> canvas.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                openFingerprint.setCanvas(canvas);
            }

        }

        webGL:
        {
            if (newEnv || !newf.getWebGL().equals(oldF.getWebGL())) {
                OpenCanvas webGL = new OpenCanvas();
                switch (newf.getWebGL()) {
                    case DEFAULT, TURN_ON -> {
                        webGL.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        webGL.setColoredPointList(canvasService.getRandCanvas());
                    }
                    case TURN_OFF -> webGL.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                openFingerprint.setWebGL(webGL);
            }
        }

        gupGL:
        {
            if (newEnv || !newf.getGupGL().equals(oldF.getGupGL())) {
                OpenCanvas gupGL = new OpenCanvas();
                switch (newf.getWebGL()) {
                    case DEFAULT, TURN_ON -> {
                        gupGL.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        gupGL.setColoredPointList(canvasService.getRandCanvas());
                    }
                    case TURN_OFF -> gupGL.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                openFingerprint.setGupGL(gupGL);
            }
        }
        WebGLDeviceWithUaDto webGlDevice = webGlDeviceService.getWebGlDevice(userAgentInfo);

        webGLDevice:
        {
            if (newEnv || !newf.getWebGLDevice().equals(oldF.getWebGLDevice())) {
                OpenWebGLDevice webGLDevice = new OpenWebGLDevice();
                switch (newf.getWebGLDevice().getType()) {
                    case DEFAULT, TURN_ON -> {
                        webGLDevice.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        webGLDevice.setRenderer(webGlDevice.getRenderer());
                        webGLDevice.setVendors(webGlDevice.getVendors());
                        webGLDevice.setGpuVendors(webGlDevice.getGpuVendors());
                        webGLDevice.setGpuArchitecture(webGlDevice.getGpuArchitecture());
                    }
                    case CUSTOMIZE -> {
                        webGLDevice.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        webGLDevice.setRenderer(newf.getWebGLDevice().getRenderer());
                        webGLDevice.setVendors(newf.getWebGLDevice().getVendors());
                        WebGlDevice webGlDevice2 = webGlDeviceRepository.findByRenderer(newf.getWebGLDevice().getRenderer());
                        if (!Objects.isNull(webGlDevice2)) {
                            webGLDevice.setGpuVendors(webGlDevice2.getGpuVendors());
                            webGLDevice.setGpuArchitecture(webGlDevice2.getGpuArchitecture());
                        } else {
                            webGlDevice2 = webGlDeviceRepository.findTopByVendors(newf.getWebGLDevice().getVendors());
                            if (!Objects.isNull(webGlDevice2)) {
                                webGLDevice.setGpuVendors(webGlDevice2.getGpuVendors());
                                webGLDevice.setGpuArchitecture(webGlDevice2.getGpuArchitecture());
                            } else {
                                webGLDevice.setGpuVendors(newf.getWebGLDevice().getVendors());
                                webGLDevice.setGpuArchitecture(newf.getWebGLDevice().getVendors());
                            }
                        }
                    }
                    case TURN_OFF -> webGLDevice.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                openFingerprint.setWebGLDevice(webGLDevice);
            }

        }
        audioContext:
        {
            if (newEnv || !newf.getAudioContext().equals(oldF.getAudioContext())) {
                OpenAudioContext audioContext = new OpenAudioContext();
                switch (newf.getAudioContext()) {
                    case DEFAULT, TURN_ON -> {
                        audioContext.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        audioContext.setNoise(getRandNoise());
                    }
                    case TURN_OFF -> audioContext.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                openFingerprint.setAudioContext(audioContext);
            }
        }

        mediaEquipment:
        {
            if (newEnv || !newf.getMediaEquipment().equals(oldF.getMediaEquipment())) {
                OpenMediaEquipment openMediaEquipment = new OpenMediaEquipment();
                switch (newf.getMediaEquipment().getType()) {
                    case DEFAULT, TURN_ON -> {
                        openMediaEquipment.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        MediaEquipmentDto mediaEquipmentDto = new MediaEquipmentDto();
                        mediaEquipmentDto.setVideoCamera(0);
                        mediaEquipmentDto.setSpeaker(1);
                        mediaEquipmentDto.setMicrophone(1);
                        openMediaEquipment.setList(mediaEquipmentService.getMediaEquipment(mediaEquipmentDto));
                    }
                    case CUSTOMIZE -> {
                        openMediaEquipment.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        openMediaEquipment.setList(mediaEquipmentService.getMediaEquipment(newf.getMediaEquipment()));
                    }
                    case TURN_OFF -> openMediaEquipment.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                openFingerprint.setMediaEquipment(openMediaEquipment);
            }
        }

        clientRects:
        {
            if (newEnv || !newf.getClientRects().equals(oldF.getClientRects())) {
                OpenClientRects clientRects = new OpenClientRects();
                switch (newf.getClientRects()) {
                    case DEFAULT, TURN_ON -> {
                        clientRects.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        Random random = new Random();
                        clientRects.setX(9 * random.nextDouble() + 0.000000001);
                        clientRects.setY(9 * random.nextDouble() + 0.000000001);
                        clientRects.setWidth(9 * random.nextDouble() + 0.000000001);
                        clientRects.setHeight(9 * random.nextDouble() + 0.000000001);
                    }
                    case TURN_OFF -> clientRects.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                openFingerprint.setClientRects(clientRects);
            }
        }

        speechVoices:
        {
            if (newEnv || !newf.getSpeechVoices().equals(oldF.getSpeechVoices())) {
                OpenSpeechVoices speechVoices = new OpenSpeechVoices();
                switch (newf.getSpeechVoices()) {
                    case DEFAULT, FOLLOW_IP -> speechVoices.setType(OpenCustomizeTypeEnum.FOLLOW_IP);
                    case TURN_ON -> {
                        speechVoices.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        speechVoices.setList(speechVoiceCountryService.getSpeechVoiceByOsAndLang(OsEnum.WIN10, null));
                    }
                }
                openFingerprint.setSpeechVoices(speechVoices);
            }
        }

        resourceInfo:
        {
            if (newEnv || !newf.getResourceInfo().equals(oldF.getResourceInfo())) {
                OpenResourceInfo resourceInfo = new OpenResourceInfo();
                switch (newf.getResourceInfo().getType()) {
                    case DEFAULT, TURN_ON -> {
                        resourceInfo.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        resourceInfo.setCpu(webGlDevice.getCpu());
                        resourceInfo.setMemory(webGlDevice.getMemory());
                    }
                    case CUSTOMIZE -> {
                        resourceInfo.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        resourceInfo.setCpu(newf.getResourceInfo().getCpu());
                        resourceInfo.setMemory(newf.getResourceInfo().getMemory());
                    }
                    case TURN_OFF -> resourceInfo.setType(OpenCustomizeTypeEnum.TURN_OFF);

                }
                openFingerprint.setResourceInfo(resourceInfo);
            }
        }

        doNotTrack:
        {
            if (newEnv || !newf.getDoNotTrack().equals(oldF.getDoNotTrack())) {
                OpenDoNotTrack doNotTrack = new OpenDoNotTrack();
                switch (newf.getDoNotTrack()) {
                    case DEFAULT, TURN_OFF -> doNotTrack.setType(OpenCustomizeTypeEnum.TURN_OFF);
                    case TURN_ON -> doNotTrack.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                }
                openFingerprint.setDoNotTrack(doNotTrack);
            }
        }

        openPort:
        {
            if (newEnv || !newf.getOpenPort().equals(oldF.getOpenPort())) {
                OpenOpenPort openOpenPort = new OpenOpenPort();
                switch (newf.getOpenPort().getType()) {
                    case DEFAULT -> {
                        openOpenPort.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        openOpenPort.setList(new ArrayList<>());
                        openOpenPort.setUrl("http://127.0.0.1:10");
                    }
                    case CUSTOMIZE -> {
                        openOpenPort.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                        openOpenPort.setList(newf.getOpenPort().getList());
                        openOpenPort.setUrl("http://127.0.0.1:10");
                    }
                    case TURN_OFF -> openOpenPort.setType(OpenCustomizeTypeEnum.TURN_OFF);
                }
                openFingerprint.setOpenPort(openOpenPort);
            }
        }

        return openFingerprint;
    }


    private String generateRandomIpAddress() {
        Random random = new Random();
        int[] ipAddressParts = new int[4];

        // 固定前两个部分为192和168
        ipAddressParts[0] = 192;
        ipAddressParts[1] = 168;

        // 生成随机的后两个部分
        ipAddressParts[2] = random.nextInt(254) + 1; // 0到255之间的随机数
        ipAddressParts[3] = random.nextInt(254) + 1; // 0到255之间的随机数

        // 将部分组合成IP地址字符串
        return String.format("%d.%d.%d.%d", ipAddressParts[0], ipAddressParts[1], ipAddressParts[2], ipAddressParts[3]);
    }

    private List<Double> getRandNoise() {
        Random random = new Random();
        int count = random.nextInt(30) + 20;
        List<Double> noise = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            noise.add(random.nextDouble() * 9 + 0.0000001);
        }
        return noise;
    }

}
