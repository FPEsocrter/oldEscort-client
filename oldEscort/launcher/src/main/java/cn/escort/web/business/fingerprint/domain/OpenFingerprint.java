package cn.escort.web.business.fingerprint.domain;

import cn.escort.web.business.fingerprint.domain.openFingerprint.*;
import cn.escort.web.business.fingerprint.domain.typeEnum.OpenUserAgent;
import cn.escort.web.business.fingerprint.domain.typeEnum.SimpleTypeEnum;
import lombok.Data;


@Data
public class OpenFingerprint {

    private SimpleTypeEnum init;

    private OpenUserAgent ua;

    private OpenUserAgentMetadata uaMetadata;

    private OpenTimeZone timeZone;

    private OpenWebRTC webRTC;

    private OpenLocation location;

    private OpenLanguage language;

    private OpenResolution resolution;

    private OpenFont font;

    private OpenCanvas canvas;

    private OpenCanvas webGL;

    private OpenCanvas gupGL;

    private OpenWebGLDevice webGLDevice;

    private OpenAudioContext audioContext;

    private OpenMediaEquipment mediaEquipment;

    private OpenClientRects clientRects;

    private OpenSpeechVoices speechVoices;

    private OpenResourceInfo resourceInfo;

    private OpenDoNotTrack doNotTrack;

    private OpenOpenPort openPort;


}
