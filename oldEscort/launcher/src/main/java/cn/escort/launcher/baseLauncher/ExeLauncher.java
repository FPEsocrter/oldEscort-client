package cn.escort.launcher.baseLauncher;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;


@Slf4j
public class ExeLauncher {


    @Getter
    @Setter
    private String exePath;

    @Getter
    @Setter
    private List<String> baseCommand;

    @Getter
    @Setter
    private List<String> keywords;


    @Getter
    private Process process;

    private FutureTask<String> readTask;

    private Queue<String> queue = new ConcurrentLinkedQueue<>();

    public ExeLauncher(String exePath){
        this.exePath=exePath;
    }

    public ExeLauncher(String exePath,List<String> baseCommand,List<String> keywords){
        this.exePath=exePath;
        this.baseCommand=baseCommand;
        this.keywords=keywords;
    }

    public void run(){
        final ArrayList<String> command = new ArrayList<>();
        command.add(exePath);
        if(!Objects.isNull(baseCommand)){
            command.addAll(baseCommand);
        }
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            process = processBuilder.start();
        }catch (IOException e){
           log.debug("exe启动异常",e.getMessage());
        }
        readTask = new FutureTask(() -> {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = errorReader.readLine()) != null) {
                if(Objects.isNull(keywords) ||keywords.isEmpty()){
                    return line;
                }else{
                    for (int i = 0; i < keywords.size(); i++) {
                        if(line.indexOf(keywords.get(i))>-1){
                            return line;
                        }
                    }
                }

            }
            return null;
        });
       new Thread(readTask).start();
    }

    public String readString(int time) throws ExecutionException, InterruptedException, TimeoutException {
       return readTask.get(time,TimeUnit.SECONDS);
    }



    public void kill() {
       process.destroy();
    }

//    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {
//        final long l = System.currentTimeMillis();
//        final ExeLauncher exeLauncher = new ExeLauncher("env\\systemFontList.exe");
//        exeLauncher.run();
//        final String str = exeLauncher.readString(5);
//        System.out.println(str);
//        ObjectMapper objectMapper = new ObjectMapper();
//        final List<FontFamilyInfo> fontFamilyInfos = objectMapper.readValue(str, new TypeReference<List<FontFamilyInfo>>() {});
//
//        System.out.println(fontFamilyInfos);
//    }
}
