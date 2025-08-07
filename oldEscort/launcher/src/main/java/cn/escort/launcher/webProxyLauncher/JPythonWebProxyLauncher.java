package cn.escort.launcher.webProxyLauncher;

import cn.escort.frameworkConfig.web.exceptionHandler.exception.BadServiceException;
import cn.escort.launcher.baseLauncher.JPythonProcess;
import cn.escort.launcher.baseLauncher.JPythonProcessLauncher;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class JPythonWebProxyLauncher implements JPythonProcessLauncher {

    private final static String baseWebProxyLauncher="..\\env\\script\\webProxy\\baseWebProxy.py";
    private final WebProxyParameters webProxyParameters;
    private final JPythonProcess jPythonProcess;
    private FutureTask<Boolean> runWebProxyTask;

    public JPythonWebProxyLauncher(WebProxyParameters webProxyParameters){
        this.webProxyParameters =webProxyParameters;
        this.webProxyParameters.setMapPort(getWebPoxyAvailablePort());
        jPythonProcess = new JPythonProcess(baseWebProxyLauncher);
        jPythonProcess.start();
        verifyFunc();
    }

    public void runWebProxy(){
        runWebProxyTask= new FutureTask<>(()-> jPythonProcess.callMethod("runWebProxy", 3000, new TypeReference<Boolean>() {}, webProxyParameters));
        new Thread(runWebProxyTask).start();
    }

    public InetAddress getInetAddress(){
        return new InetAddress("127.0.0.1", webProxyParameters.getMapPort(), webProxyParameters.getType().ordinal());
    }

    public  Boolean isDone(){
        if(Objects.isNull(runWebProxyTask)){
            runWebProxy();
        }
        try {
            final Boolean aBoolean = runWebProxyTask.get();
            if(!Boolean.TRUE.equals(aBoolean)){
                throw new BadServiceException("运行 jPython runWebProxy异常"+aBoolean);
            }
        } catch (InterruptedException |ExecutionException e) {
            throw new BadServiceException("运行 jPython runWebProxy异常",e);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean kill() {
        return jPythonProcess.kill();
    }

    @Override
    public Boolean verifyFunc() {
        return jPythonProcess.verifyFunc(List.of("runWebProxy","kill"));
    }

    private Integer getWebPoxyAvailablePort(){
        try (ServerSocket serverSocket = new ServerSocket(0)){
            return serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new BadServiceException("获取端口异常",e);
        }
    }

    @SneakyThrows(InterruptedException.class)
    public static void main(String[] args) {
        final JPythonWebProxyLauncher jPythonWebProxyLauncher = new JPythonWebProxyLauncher( new WebProxyParameters("127.0.0.1",7890, WebProxyEnum.SOCKS));
        final Boolean done = jPythonWebProxyLauncher.isDone();
        System.out.println(done);
        Thread.sleep(10000);
        jPythonWebProxyLauncher.kill();
    }
}
