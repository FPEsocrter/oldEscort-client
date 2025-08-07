package cn.escort.launcher.networkDetectionLauncher;

import cn.escort.frameworkConfig.web.exceptionHandler.exception.BadServiceException;
import cn.escort.launcher.baseLauncher.JPythonProcess;
import cn.escort.launcher.baseLauncher.JPythonProcessLauncher;
import cn.escort.launcher.webProxyLauncher.InetAddress;
import cn.escort.launcher.webProxyLauncher.WebProxyParameters;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class JPythonNetworkDetectionLauncher implements JPythonProcessLauncher {

    private final static String baseNetworkDetectionPath="..\\env\\script\\networkDetection\\baseNetworkDetection.py";
    private final JPythonProcess jPythonProcess;
    private final InetAddress inetAddress;
    private IpDetails ipDetails;

    public JPythonNetworkDetectionLauncher(InetAddress inetAddress){
        this.inetAddress=inetAddress;
        jPythonProcess = new JPythonProcess(baseNetworkDetectionPath);
        jPythonProcess.start();
        verifyFunc();
    }
    public IpDetails getCacheIpInfo(){
        if(Objects.nonNull(ipDetails)){
            return ipDetails;
        }
        return getIpInfo();
    }

    public IpDetails getIpInfo(){
        IpDetails temp = jPythonProcess.callMethod("getIpInfo", 35, new TypeReference<IpDetails>(){}, inetAddress);
        if(Objects.nonNull(temp)&&Objects.nonNull(temp.getIps())&&!temp.getIps().isEmpty()){
            ipDetails=temp;
        }else {
            ipDetails=null;
        }
        return ipDetails;
    }

    @Override
    public Boolean kill() {
        jPythonProcess.kill();
        return Boolean.TRUE;
    }

    @Override
    public Boolean verifyFunc() {
        return jPythonProcess.verifyFunc(List.of("kill","getIpInfo"));
    }

    public static void main(String[] args) {
        final JPythonNetworkDetectionLauncher jPythonNetworkDetectionLauncher = new JPythonNetworkDetectionLauncher(new InetAddress("127.0.0.1", 7890, 3));
        final IpDetails ipInfo = jPythonNetworkDetectionLauncher.getIpInfo();
        System.out.println(ipInfo);
    }


}
