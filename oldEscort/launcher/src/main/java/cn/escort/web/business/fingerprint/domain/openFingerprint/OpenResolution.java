package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenResolution implements Serializable {

    private OpenCustomizeTypeEnum type;

    private Integer windowWidth;
    private Integer windowHeight;

    private Integer monitorWidth;
    private Integer monitorHeight;

}
