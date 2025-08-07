package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

import java.util.Map;

@Data
public class OpenFont {

    private OpenCustomizeTypeEnum type;

    private Map<String, FontInfo> fontMap;

}
