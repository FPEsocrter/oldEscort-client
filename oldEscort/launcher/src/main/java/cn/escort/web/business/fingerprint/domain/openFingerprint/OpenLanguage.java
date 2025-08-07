package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenLanguage implements Serializable {

    private OpenCustomizeTypeEnum type;
    //界面语言
    private String interfaceLanguage;
    //语言列表
    private List<String> languages;

}
