package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.SimpleTypeEnum;
import lombok.Data;

@Data
public class OpenClientRects {

  private OpenCustomizeTypeEnum type;

  private Double x; //1-10

  private Double y;

  private Double width;

  private Double height;

}
