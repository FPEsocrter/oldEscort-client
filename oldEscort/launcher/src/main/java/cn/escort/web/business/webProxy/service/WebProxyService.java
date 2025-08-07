package cn.escort.web.business.webProxy.service;

import cn.escort.web.business.environment.domain.WebProxy;
import cn.escort.web.business.environment.domain.dto.WebProxyDto;
import cn.escort.launcher.networkDetectionLauncher.IpDetails;
import cn.escort.web.business.webProxy.domain.IpInfo;

import java.util.Map;


public interface WebProxyService {

    IpInfo detection(WebProxyDto webProxy);

    WebProxy getWebProxy(Map<String,String> proxyInfo);
}
