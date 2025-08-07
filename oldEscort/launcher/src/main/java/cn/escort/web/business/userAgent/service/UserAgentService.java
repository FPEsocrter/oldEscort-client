package cn.escort.web.business.userAgent.service;

import cn.escort.web.business.fingerprint.domain.openFingerprint.OpenUserAgentMetadata;
import cn.escort.web.business.userAgent.domain.UserAgentInfo;

import java.util.List;

public interface UserAgentService {

    List<Integer> getUaMajorVersion();

    String getUaByVersion(List<Integer> uaVersion);


    UserAgentInfo getUserAgentInfoByUserAgent(String UserAgent);

    OpenUserAgentMetadata getOpenUserAgentMetadata(UserAgentInfo userAgentInfo);

}
