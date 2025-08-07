package cn.escort.launcher;

import cn.escort.frameworkConfig.web.exceptionHandler.exception.BadServiceException;
import cn.escort.launcher.chromiumLauncher.AipKey;
import cn.escort.launcher.chromiumLauncher.Chromium;
import cn.escort.launcher.chromiumLauncher.ChromiumLauncher;
import cn.escort.launcher.networkDetectionLauncher.JPythonNetworkDetectionLauncher;
import cn.escort.launcher.networkDetectionLauncher.IpDetails;
import cn.escort.launcher.webProxyLauncher.InetAddress;
import cn.escort.launcher.webProxyLauncher.JPythonWebProxyLauncher;
import cn.escort.launcher.webProxyLauncher.WebProxyParameters;
import cn.escort.utils.crypto.AesUtils;
import cn.escort.web.business.chromiumData.domain.ChromiumData;
import cn.escort.web.business.environment.domain.WebProxy;
import cn.escort.web.business.fingerprint.domain.OpenFingerprint;
import cn.escort.web.business.fingerprint.domain.typeEnum.FollowIpTypeEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
public class Launcher {

    @Getter
    private ChromiumLauncher chromiumLauncher;
    @Getter
    private JPythonWebProxyLauncher webProxyLauncher;
    @Getter
    private JPythonNetworkDetectionLauncher jPythonNetworkDetectionLauncher;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Integer webPort;

    @Getter
    @Setter
    private OpenFingerprint openFingerprint;

    @Getter
    @Setter
    private WebProxy webProxy;
    @Getter
    @Setter
    private ChromiumData chromiumData;

    @Getter
    @Setter
    private Function<String,String> langByCountryCode;
    @Getter
    private String lang;

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static byte[] aesKey="oyb8ZvjMd+VvP/mQ".getBytes();

    public Launcher(Long id,Integer webPort,OpenFingerprint openFingerprint,
                    WebProxy webProxy,ChromiumData chromiumData,Function<String,String> langByCountryCode){
        this.id=id;
        this.webPort=webPort;
        this.openFingerprint=openFingerprint;
        this.webProxy=webProxy;
        this.chromiumData=chromiumData;
        this.langByCountryCode=langByCountryCode;
    }

    public void run() {
        InetAddress webProxyInetAddress =new InetAddress();
        if(!WebProxyEnum.NO_PROXY.equals(webProxy.getType())){
            System.out.println(webProxy);
            WebProxyParameters webProxyParameters = new WebProxyParameters();
            BeanUtils.copyProperties(webProxy,webProxyParameters);
            System.out.println(webProxyParameters);
            webProxyLauncher = new JPythonWebProxyLauncher(webProxyParameters);
            webProxyLauncher.runWebProxy();
            webProxyInetAddress = webProxyLauncher.getInetAddress();
        }else{
            webProxyInetAddress.setPort(WebProxyEnum.NO_PROXY.ordinal());
        }

        if(OpenCustomizeTypeEnum.FOLLOW_IP.equals(openFingerprint.getLanguage().getType())){
            if(!WebProxyEnum.NO_PROXY.equals(webProxy.getType())){
                 webProxyLauncher.isDone();
            }
            jPythonNetworkDetectionLauncher= new JPythonNetworkDetectionLauncher(webProxyInetAddress);
            IpDetails  ipInfo = jPythonNetworkDetectionLauncher.getIpInfo();
            if(Objects.nonNull(ipInfo)){
                lang = langByCountryCode.apply(ipInfo.getCountryCode());
            }else{
                lang="en";
            }
        }else if(OpenCustomizeTypeEnum.CUSTOMIZE.equals(openFingerprint.getLanguage().getType())){
            lang =openFingerprint.getLanguage().getInterfaceLanguage();
        }

        final AipKey aipKey = new AipKey(id, webPort, System.currentTimeMillis()/1000);
        String aipKeyStr="";
        try {
            aipKeyStr = objectMapper.writeValueAsString(aipKey);
        } catch (JsonProcessingException e) {
            log.error(aipKey.toString());
            log.error(e.getMessage());
            throw new BadServiceException(e.getMessage());
        }
        aipKeyStr = AesUtils.diyEncrypt(aipKeyStr,aesKey);

        Chromium chromium = new Chromium(aipKeyStr,webProxyInetAddress,chromiumData,openFingerprint.getUa().getUserAgent(),lang);

        chromiumLauncher = new ChromiumLauncher(chromium);
        chromiumLauncher.run();

    }

    public static void main(String[] args) throws IOException {
        final long epochMilli = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toEpochSecond();
        final LocalDateTime now = LocalDateTime.now();
        final int hour = now.getHour();
        final int minute = now.getMinute();
        final long timestamp = epochMilli + 1234567 + hour * 789 + minute/10;
        AipKey aipKey = new AipKey(12L, 8080, timestamp);
        String aipKeyStr = objectMapper.writeValueAsString(aipKey);
        aipKeyStr = AesUtils.diyEncrypt(aipKeyStr,aesKey);
        System.out.println("--api-key="+aipKeyStr);
//        final File file = new File("E://openFingerprint.json");
//        final String sJson = Files.readString(Paths.get(file.getPath()));
//        final HashMap hashMap = objectMapper.readValue(sJson, HashMap.class);
//        final String data = String.valueOf(hashMap.get("data"));
//        System.out.println(data);
//        final String s = AesUtils.diyDecrypt(data, "KDHpjtQuysmq8rVO".getBytes());
//        System.out.println(s);

    }

}

