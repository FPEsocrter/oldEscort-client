package cn.escort.web.business.font.service;

import cn.escort.web.business.fingerprint.domain.openFingerprint.FontInfo;
import cn.escort.web.business.font.domain.FontFamilyInfo;
import cn.escort.web.business.userAgent.domain.UserAgentInfo;

import java.util.List;
import java.util.Map;

public interface FontService {
    List<String> getFontListAll();

    List<String> getRandFontList(String userAgent);

    List<String> getRandFontList(UserAgentInfo userAgentInfo);

    public List<String> getFontList(String userAgent,String code);
    public List<String> getFontList(UserAgentInfo userAgentInfo,String code);

    Map<String, FontInfo> getFontMap(List<String> fontList);


    public List<FontFamilyInfo> getSystemFontList();

}
