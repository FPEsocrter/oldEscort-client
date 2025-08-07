package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

@Data
public class OpenWebRTC {

    private OpenCustomizeTypeEnum type;

    private String privateIp;

    private String publicIp;

}
