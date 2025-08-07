package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenResourceInfo implements Serializable {

     private OpenCustomizeTypeEnum type;

     private Integer cpu;
     private Integer memory;
}
