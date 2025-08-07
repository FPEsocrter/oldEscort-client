package cn.escort.launcher.chromiumLauncher;

import cn.escort.frameworkConfig.web.exceptionHandler.exception.BadServiceException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ChromiumLauncher{

    private static String baseChromiumLauncher="..\\core\\chromium\\chrome.exe";
    //private static String baseChromiumLauncher="D:\\chromium\\chromium\\src\\out\\Default\\chrome.exe";

    @Getter
    private Process process;

    private FutureTask<Integer> chromiumServicePort;

    private Integer port;

    private List<String> chromeCommand=new ArrayList<>();

    @Getter
    @Setter
    private Chromium chromium;

    public ChromiumLauncher(String... OpenUrls){
        baseCommand();
        chromeCommand.add("--new-tab");
        chromeCommand.addAll(Arrays.asList(OpenUrls));
    }

    public ChromiumLauncher(Chromium chromium){
            this.chromium=chromium;
    }

    @SneakyThrows(IOException.class)
    public void baseCommand(){
        final File file = new File(baseChromiumLauncher).getCanonicalFile();
        if(!file.exists()||!file.isFile()){
            throw new BadServiceException(file.getAbsolutePath()+"为找到");
        }
        chromeCommand.add(file.getAbsolutePath());
        chromeCommand.add("--remote-debugging-port=0");
    }
    public void dealWith(List<String> dealWithChromeCommand){
        // chromeCommand.addAll(dealWithChromeCommand);
    }

    public void run(){
        baseCommand();
        chromeCommand.add("--api-key="+chromium.getAipKey());
        if(chromium.getWebProxyInetAddress().webProxyType>0){
            chromeCommand.add("--proxy-server=socks5://"+chromium.getWebProxyInetAddress().getAddress()+":"+chromium.getWebProxyInetAddress().getPort());
        }
        File currentDirectory = new File(System.getProperty("user.dir"));
        final String parent = currentDirectory.getParent();
        chromeCommand.add("--user-data-dir="+parent+"/env/envData/"+chromium.getChromiumData().getPath());
        chromeCommand.add("--user-agent="+chromium.getShowUserAgent());
        if(StringUtils.isNoneBlank(chromium.getLang())){
            chromeCommand.add("--lang="+chromium.getLang());
        }
        dealWith(chromeCommand);
        log.info("chromeCommand{}",chromeCommand);
        ProcessBuilder processBuilder = new ProcessBuilder(chromeCommand);
        Map<String, String> environment = processBuilder.environment();
        // 设置环境变量
        environment.put("GOOGLE_API_KEY", "no");
        environment.put("GOOGLE_DEFAULT_CLIENT_ID", "no");
        environment.put("GOOGLE_DEFAULT_CLIENT_SECRET", "no");
        try {
            process = processBuilder.start();
        }catch (IOException e){
           log.debug("chromium启动异常",e.getMessage());
        }
        chromiumServicePort = new FutureTask(() -> {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            int index=0;
            while ((line = errorReader.readLine()) != null) {
                String pattern = "ws://127.0.0.1:(\\d+)/devtools/browser/";
                Pattern regex = Pattern.compile(pattern);
                Matcher matcher = regex.matcher(line);
                if (matcher.find()) {
                    String port = matcher.group(1);
                    errorReader.close();
                    return Integer.valueOf(port);
                }
            }
            return null;
        });
        new Thread(chromiumServicePort).start();;
    }

    public Integer getPort(){
        if(Objects.isNull(chromiumServicePort)){
            throw  new BadServiceException("chromium未启动");
        }
        if(Objects.isNull(port)){
            try {
                port=chromiumServicePort.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException |ExecutionException |TimeoutException e) {
                throw new BadServiceException("chromium未启动",e);
            }
        }
        return port;
    }

    public void kill() {
        process.destroy();
    }

    public static void main(String[] args) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        final String parent = currentDirectory.getParent();
        System.out.println(parent);
    }
}
