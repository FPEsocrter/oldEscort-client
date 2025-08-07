package cn.escort.launcher.baseLauncher;

import cn.escort.frameworkConfig.web.exceptionHandler.exception.BadServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class JPythonProcess {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private static final String pythonPath ="..\\env\\python\\python.exe";
    private static final String reJPython="reJPython;";
    private Process process;
    private PrintWriter writer;
    private BufferedReader inputReader;
    private Thread queueThread;
    private final ConcurrentLinkedQueue<String> queue;
    private final String scriptPath;
    public JPythonProcess(String scriptPath){
        this.scriptPath=scriptPath;
        queue = new ConcurrentLinkedQueue<>();
    }

    public void start(){
        File scriptFile=null;
        File pythonFile=null;
        try {
            scriptFile=new File(scriptPath).getCanonicalFile();
            pythonFile = new File(pythonPath).getCanonicalFile();
        }catch (IOException e){
            log.error(e.getMessage());
        }
        if(!scriptFile.exists()||!scriptFile.isFile()){
            throw new BadServiceException(scriptFile.getAbsolutePath()+"文件不存在");
        }
        if(!pythonFile.exists()||!pythonFile.isFile()){
            throw new BadServiceException(pythonFile.getAbsolutePath()+"文件不存在");
        }
        final ArrayList<String> commands = new ArrayList<>();
        commands.add(pythonFile.getAbsolutePath());
        commands.add("-i");
        commands.add(scriptFile.getAbsolutePath());
        log.debug("JPythonProcess.processBuilder{}",commands);
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        final File workDirectory = scriptFile.getParentFile().getAbsoluteFile();
        log.debug("workDirectory:{}", workDirectory);
        processBuilder.directory(workDirectory);
        try {
            process = processBuilder.start();
        }catch (IOException e){
            log.error("JPythonProcess启动异常{}",e.getMessage());
        }
        inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        queueThread = new Thread(() -> {
            String result = null;
            while (true) {
                try {
                    if (!((result = inputReader.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                log.debug("inputReader_result: {}",result);
                queue.add(result);
            }
        });
        queueThread.start();
        // 创建一个写入器，用于向Python发送数据
        writer = new PrintWriter(process.getOutputStream());
        writer.println("print(0)");
        String jpy= """
                    import json
                    import sys
                    sys.stdout.reconfigure(encoding='utf-8')
                    """;
        log.debug(jpy);
        writer.println(jpy); // 发送方法名
        writer.flush();
    }

    @SneakyThrows(InterruptedException.class)
    private String getReJPython(Function<String,Boolean> function,int time){
        Thread.sleep(1);
        final long startTime = System.currentTimeMillis() ;
        int count=0;
        int tIndex=queue.size()+1;
        while (true){
            final String pollStr = queue.poll();
            log.debug("queue.poll:{},len:{}",pollStr,pollStr!=null?pollStr.length():0);
            if(pollStr==null){
                Thread.sleep(100);
            }else{
                if(pollStr.indexOf(reJPython)==0){
                    final String substring = pollStr.substring(reJPython.length());
                    final Boolean apply = function.apply(substring);
                    if(apply){
                        return substring;
                    }else{
                        queue.add(substring);
                    }
                }
            }
            count++;
            if(count%tIndex==0){
                Thread.sleep(100);
                tIndex=queue.size()+1;
            }
            if(count%20==0){
                if(System.currentTimeMillis() -startTime>time){
                    return "";
                }
            }
        }
    }

    public Boolean verifyFunc(List<String> funcList){
        if(Objects.isNull(process)){
            throw new BadServiceException("JPythonProcess未启动JPythonProcess");
        }
        StringBuilder jPy=new StringBuilder("\n");
        jPy.append("flag = True").append("\n");
        jPy.append("try:").append("\n");
        jPy.append("    ").append("\n");
        for (String funcName:funcList) {
            jPy.append("    jPythonFunName = ").append(funcName).append(".__name__").append("\n");
        }
        jPy.append("except Exception as e:").append("\n");
        jPy.append("    flag = False").append("\n");
        jPy.append("\n");
        jPy.append("print('"+reJPython+"verifyFunc%s'%flag)");
        log.debug("{}",jPy);
        writer.println(jPy);
        writer.flush();
        final String result = getReJPython(str -> str.indexOf("verifyFunc") == 0,500);
        final String lastResult = result.replace("verifyFunc", "");
        return  Boolean.valueOf(lastResult);
    }

    @SneakyThrows({JsonProcessingException.class,InterruptedException.class})
    public <T> T callMethod(String methodName, long timeout, TypeReference<T> t, Object ...args){
        if(Objects.isNull(process)){
            throw new BadServiceException("JPythonProcess未启动JPythonProcess");
        }
        for (int i = 0; i < args.length; i++) {
            String jsonString = objectMapper.writeValueAsString(args[i]);
            String jPy = "args" +
                    i +
                    " = json.loads(" +
                    "\"\"\"" +
                    jsonString +
                    "\"\"\")";
            log.debug(jPy);
            writer.println(jPy);
            log.debug("print(args"+i+")");
            writer.println("print(args"+i+")");
            writer.flush();
            Thread.sleep(1);
        }
        StringBuffer jPy = new StringBuffer();
        jPy.append("try:").append("\n");
        if(Objects.nonNull(t)) {
            jPy.append("    reJson = ");
        }
        jPy.append(methodName);
        jPy.append("(");
        for (int i = 0; i < args.length; i++) {
            jPy.append("args");
            jPy.append(i);
            if(i!=args.length-1){
                jPy.append(",");
            }
        }
        jPy.append(")").append("\n");;
        jPy.append("    print('"+reJPython+"ReJson;%s' % json.dumps(reJson))").append("\n");
        jPy.append("except Exception as e:").append("\n");
        jPy.append("    print('Exception:%s' % e)").append("\n");
        jPy.append("    print('"+reJPython+"ReJson;%s' % json.dumps({}))").append("\n");
        log.debug("\n{}",jPy);
        writer.println(jPy);
        writer.flush();
        if(Objects.nonNull(t)){
            final String result = getReJPython(str -> str.indexOf("ReJson;") == 0,2000);
            final String lastResult = result.replace("ReJson;", "");
            if(StringUtils.isBlank(lastResult)||lastResult.equals("{}")){
                return null;
            }
            return objectMapper.readValue(lastResult, t);
        }
        return null;
    }
    public Boolean kill(){
        if(Objects.isNull(process)){
            throw new BadServiceException("JPythonProcess未启动JPythonProcess");
        }
        if(queueThread.isAlive()){
            queueThread.interrupt();
        }
        Boolean killFla = callMethod("kill",500, new TypeReference<Boolean>() {});
        process.destroy();
        return killFla;
    }

    public  Process getProcess(){
        return Objects.requireNonNull(process,"进程为空");
    }

}
