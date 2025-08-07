package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenOpenPort implements Serializable {

    private OpenCustomizeTypeEnum type;

    private List<Integer> list;

    private String url;

}
