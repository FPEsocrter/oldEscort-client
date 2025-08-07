package cn.escort.web.business.webProxy.service;

import cn.escort.frameworkConfig.web.exceptionHandler.exception.BadServiceException;
import cn.escort.launcher.networkDetectionLauncher.JPythonNetworkDetectionLauncher;
import cn.escort.launcher.webProxyLauncher.InetAddress;
import cn.escort.launcher.webProxyLauncher.JPythonWebProxyLauncher;
import cn.escort.launcher.webProxyLauncher.WebProxyParameters;
import cn.escort.web.business.environment.domain.WebProxy;
import cn.escort.web.business.environment.domain.dto.WebProxyDto;
import cn.escort.launcher.networkDetectionLauncher.IpDetails;
import cn.escort.web.business.webProxy.domain.IpInfo;
import cn.escort.web.business.webProxy.domain.WebProxyColEnum;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebProxyServiceImpl implements WebProxyService {


    @Override
    public IpInfo detection(WebProxyDto webProxy) {
        WebProxyParameters webProxyParameters = new WebProxyParameters();
        BeanUtils.copyProperties(webProxy,webProxyParameters);

        JPythonWebProxyLauncher jPythonWebProxyLauncher = new JPythonWebProxyLauncher(webProxyParameters);
        jPythonWebProxyLauncher.runWebProxy();

        InetAddress inetAddress = jPythonWebProxyLauncher.getInetAddress();
        JPythonNetworkDetectionLauncher jPythonNetworkDetectionLauncher = new JPythonNetworkDetectionLauncher(inetAddress);
        jPythonWebProxyLauncher.isDone();
        try {
             IpDetails ipDetails = jPythonNetworkDetectionLauncher.getIpInfo();
             if(Objects.nonNull(ipDetails)){
                 IpInfo ipInfo = new IpInfo();
                 BeanUtils.copyProperties(ipDetails,ipInfo);
                 return ipInfo;
             }else{
                throw new BadServiceException("代理检测异常");
             }
        }finally {
            jPythonWebProxyLauncher.kill();
            jPythonNetworkDetectionLauncher.kill();
        }
    }


    public WebProxy getWebProxy(Map<String,String> proxyInfo){
        WebProxy webProxy = new WebProxy();
        webProxy.setType(WebProxyEnum.values()[Integer.parseInt(proxyInfo.get(WebProxyColEnum.TYPE.getName()))]);
        webProxy.setHost(proxyInfo.get(WebProxyColEnum.HOST.getName()));
        String portStr = proxyInfo.get(WebProxyColEnum.PORT.getName());
        if(StringUtils.isNotBlank(portStr)){
            webProxy.setPort(Integer.valueOf(portStr));
        }

        webProxy.setAccount(proxyInfo.get(WebProxyColEnum.ACCOUNT.getName()));
        webProxy.setPassword(proxyInfo.get(WebProxyColEnum.PASSWORD.getName()));

        proxyInfo.remove(WebProxyColEnum.TYPE.getName());
        proxyInfo.remove(WebProxyColEnum.HOST.getName());
        proxyInfo.remove(WebProxyColEnum.PORT.getName());
        proxyInfo.remove(WebProxyColEnum.ACCOUNT.getName());
        proxyInfo.remove(WebProxyColEnum.PASSWORD.getName());

        webProxy.setOther(proxyInfo);
        return webProxy;
    }


/*    public static void main(String[] args) {
        WebProxyParameters webProxyParameters = new WebProxyParameters();
        webProxyParameters.setMapPort(7890);
        webProxyParameters.setHost("127.0.0.1");
        webProxyParameters.setPort(8083);
        webProxyParameters.setType(WebProxyEnum.SOCKS);
        webProxyParameters.setMapPort(JPythonWebProxyLauncher.getWebPoxyAvailablePort());

        JPythonWebProxyLauncher jPythonWebProxyLauncher = new JPythonWebProxyLauncher(webProxyParameters);
        jPythonWebProxyLauncher.runWebProxy();

        InetAddress inetAddress = jPythonWebProxyLauncher.getInetAddress(Boolean.FALSE);
        JPythonNetworkDetectionLauncher jPythonNetworkDetectionLauncher = new JPythonNetworkDetectionLauncher(inetAddress);
        jPythonWebProxyLauncher.getInetAddress();
        try {
            IpDetails ipDetails = jPythonNetworkDetectionLauncher.getIpInfo(5);
            IpInfo ipInfo = new IpInfo();
            BeanUtils.copyProperties(ipDetails,ipInfo);
            System.out.println(ipInfo);
        }catch (TimeoutException e){
            throw new BadServiceException("网络超时");
        }finally {
            jPythonWebProxyLauncher.kill();
            jPythonNetworkDetectionLauncher.kill();
        }

    }*/
}
