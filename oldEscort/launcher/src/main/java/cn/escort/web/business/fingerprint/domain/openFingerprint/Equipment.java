package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.mediaEquipment.domain.MediaEquipmentEnum;
import lombok.Data;

@Data
public class Equipment {
    //0 是麦克风 摄像头 2音响
    private MediaEquipmentEnum type;

    private String  label;

    private String deviceId;

    private String groupId;

}
