package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.ByUaEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenWebGLDevice implements Serializable {

   private OpenCustomizeTypeEnum type;

   private String vendors;
   private String renderer;

   private String gpuVendors;
   private String gpuArchitecture;



}
