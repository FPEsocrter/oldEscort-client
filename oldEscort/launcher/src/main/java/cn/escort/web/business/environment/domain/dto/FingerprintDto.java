package cn.escort.web.business.environment.domain.dto;


import cn.escort.web.business.environment.domain.dto.fingerprint.*;
import cn.escort.web.business.fingerprint.domain.typeEnum.SimpleTypeEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.OnlyFollowIpEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.WebRTCEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema( description= "指纹信息")
public class FingerprintDto implements Serializable {


    @NotNull(message = "时区选项不能空")
    private TimeZoneDto timeZone;


    @NotNull(message = "webRTC选项不能空")
    private WebRTCEnum webRTC;


    @NotNull(message = "地理位置选项不能空")
    private LocationDto location;


    @NotNull(message = "语言选项不能空")
    private LanguageDto language;


    @NotNull(message = "分辨率选项不能空")
    private ResolutionDto resolution;

    @NotNull(message = "字体选项不能空")
    private FontDto font;

    @NotNull(message = "canvas选项不能空")
    private SimpleTypeEnum canvas;

    @NotNull(message = "webGL选项不能空")
    private SimpleTypeEnum webGL;

    @NotNull(message = "gupGL选项不能空")
    private SimpleTypeEnum gupGL;

    @NotNull(message = "WebGL元数据选项不能空")
    private WebGLDeviceDto webGLDevice;

    @NotNull(message = "AudioContext选项不能空")
    private SimpleTypeEnum audioContext;

    @NotNull(message = "媒体设备选项不能空")
    private MediaEquipmentDto mediaEquipment;


    @NotNull(message = "ClientRects选项不能空")
    private SimpleTypeEnum clientRects;

    @NotNull(message = "SpeechVoices选项不能空")
    private OnlyFollowIpEnum speechVoices;

    @NotNull(message = "设备资源信息不能空")
    private ResourceInfoDto resourceInfo;

    @NotNull(message = "doNotTrack选项不能空")
    private SimpleTypeEnum doNotTrack;

    @NotNull(message = "端口扫描保护选项不能空")
    private OpenPortDto openPort;

    @Schema(description = "是否新的指纹")
    private Boolean newEvn=false;

}
