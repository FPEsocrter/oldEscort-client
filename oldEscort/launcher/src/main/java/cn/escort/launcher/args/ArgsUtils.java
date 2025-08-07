package cn.escort.launcher.args;

import cn.escort.utils.crypto.AesUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ArgsUtils {

    private static final String args_args="./args.pop";
    private static final List<String> allowArgs = List.of("--timestamp", "--server.port");
    private final static byte[] argsAesKey = "b04d265ac800a83c3f1587ea0ae15411".getBytes(StandardCharsets.UTF_8);

    @SneakyThrows(IOException.class)
    public Map<String, String> toFileArgs() {
        final File file = new File("./args.pop").getCanonicalFile();

        if (!file.exists() || !file.isFile()) {
            return new HashMap<>();
        }
        List<String> argList = null;
        try {
            argList = Files.readAllLines(Path.of(file.getPath()));
        } catch (IOException e) {
            log.error("无文件args.pop", e);
            return new HashMap<>();
        }
        return argStoMap(argList);
    }

    public Map<String, String> argStoMap(List<String> argList) {
        final Map<String, String> argMap = new HashMap<>();
        if (argList.isEmpty()) {
            return argMap;
        }
        final Map<String, String> collect = argList.stream()
                .map(str -> str.split("=", 2))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1]));
        argMap.putAll(collect);
        return argMap;
    }

    @SneakyThrows(IOException.class)
    public static void main(String[] args) {

        final HashMap<String, String> fileArgMap = new HashMap<>();
        fileArgMap.put("--timestamp", AesUtils.diyEncrypt(String.valueOf(getSuTimestamp()), argsAesKey));
        fileArgMap.put("--server.port", AesUtils.diyEncrypt("8080", argsAesKey));

        final List<String> fileArgList = fileArgMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .toList();

        fileArgList.forEach(System.out::println);
        final File file = new File("./args.pop").getCanonicalFile();
        Files.write(Paths.get(file.getPath()), fileArgList, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

    }

    public String[] run(String[] args) {
        final Map<String, String> argMap = argStoMap(List.of(args));
        final Map<String, String> fileArgs = toFileArgs();
        argMap.putAll(fileArgs);

        final Map<String, String> allowArgMap = argMap.entrySet().stream()
                .filter(entry -> allowArgs.contains(entry.getKey()))
                .peek(entry -> {
                    final String value = AesUtils.diyDecrypt(entry.getValue(), argsAesKey);
                    entry.setValue(value);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        final Set<String> allowArgKey = allowArgMap.keySet();
        final boolean equals = new HashSet<>(allowArgs).equals(allowArgKey);
        if (!equals) {
            log.info("启动参数异常 参数异常");
            throw new RuntimeException("启动参数异常 参数异常");
        }
        final long timestampMillis = System.currentTimeMillis();

        final String timestampStr = allowArgMap.get(allowArgs.get(0));
        final long argTimestamp = Long.parseLong(timestampStr);
        final long time = timestampMillis / 1000 - argTimestamp;

        final long suTimestamp = getSuTimestamp();

        boolean flag = (-2 < time && time < 5) || argTimestamp == suTimestamp;
        if (!flag) {
            log.info("时间戳校验失败");
            throw new RuntimeException("时间戳校验失败");
        }
        allowArgMap.remove(allowArgs.get(0));

        return allowArgMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .toArray(String[]::new);
    }

    private static long getSuTimestamp() {
        final long epochMilli = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toEpochSecond();
        final LocalDateTime now = LocalDateTime.now();
        final int hour = now.getHour();
        final int minute = now.getMinute();
        return epochMilli + 7654321 + hour * 987 + minute / 10;
    }

}
