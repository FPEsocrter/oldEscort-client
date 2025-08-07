package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OnlyFollowIpEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import cn.escort.web.business.speechVoices.domain.SpeechVoice;
import lombok.Data;

import java.util.List;

@Data
public class OpenSpeechVoices {

    private OpenCustomizeTypeEnum type;

    private List<SpeechVoice> list;

}
