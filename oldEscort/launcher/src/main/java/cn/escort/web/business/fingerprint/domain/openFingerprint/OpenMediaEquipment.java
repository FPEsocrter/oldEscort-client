package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class OpenMediaEquipment {

    private OpenCustomizeTypeEnum type;

    private List<Equipment> list;


}
