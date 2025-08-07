package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenPort implements Serializable {

    private CustomizeTypeEnum type;

    private List<Integer> list;

}
