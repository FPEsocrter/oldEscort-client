package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class OpenAudioContext {

    private OpenCustomizeTypeEnum type;

    private List<Double> noise;

}

