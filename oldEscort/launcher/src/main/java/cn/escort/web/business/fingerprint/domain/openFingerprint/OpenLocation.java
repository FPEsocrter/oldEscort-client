package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenLocation implements Serializable {

    private OpenCustomizeTypeEnum type;

    private Boolean permissions =Boolean.FALSE;

    private Float latitude;
    private Float longitude;
    private Float accuracy;

}
