package cn.escort.web.business.userAgent.service;

import cn.escort.web.business.fingerprint.domain.openFingerprint.OpenUserAgentMetadata;
import cn.escort.web.business.font.domain.INOSEnum;
import cn.escort.web.business.userAgent.domain.*;
import cn.escort.web.business.userAgent.repository.UaVersionRepository;
import cn.escort.web.business.userAgent.repository.UserAgentMetadataInfoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserAgentServiceImpl implements UserAgentService {

    private final UaVersionRepository uaVersionRepository;

    private final UserAgentMetadataInfoRepository userAgentMetadataInfoRepository;

    private  Map<String, BiFunction<Matcher,String, UserAgentInfo>> userAgentMap =new LinkedHashMap<>();
    private Map<INOSEnum, Function<UserAgentInfo, OpenUserAgentMetadata>> uaMetadataMap =new LinkedHashMap<>();


    @Override
    public List<Integer> getUaMajorVersion() {
        return  uaVersionRepository.getUaMajorVersion().stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }


    @Override
    public String getUaByVersion(List<Integer> list) {
        UaVersion uaVersion= null;
        if(list.size()==1&&list.get(0).equals(Integer.MAX_VALUE)){
             uaVersion = uaVersionRepository.findFirstByOrderByIdDesc();
        }else{
            List<UaVersion> listUa= uaVersionRepository.findByUaMajorVersionIn(list);
            if(Objects.isNull(listUa)||listUa.isEmpty()){
                uaVersion = uaVersionRepository.findFirstByOrderByIdDesc();
            }else{
                Random random = new Random();
                int randomIndex = random.nextInt(listUa.size());
                uaVersion =listUa.get(randomIndex);
            }
        }
        String baseUa = "Mozilla/5.0 (Windows NT %s; %s) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/%s Safari/537.36";
        return String.format(baseUa, "10.0", "Win64; x64", uaVersion.getVersion());
    }


    @PostConstruct
    public void init(){
        userAgentMap.put(
                "Mozilla/5.0 \\(Windows NT (.*?); (.*?)\\) AppleWebKit/537\\.36 \\(KHTML, like Gecko\\) Chrome/(.*?) Safari/537\\.36",
                this::getWinUserAgent
        );

        userAgentMap.put(
                "Mozilla/5.0 \\(Windows NT (.*?); (.*?)\\) AppleWebKit/537\\.36 \\(KHTML, like Gecko\\) Chromium/(.*?) Safari/537\\.36",
                this::getWinUserAgent
        );

        uaMetadataMap.put(INOSEnum.WIN32, this::getWinUaMetadata);
        uaMetadataMap.put(INOSEnum.MOBILE,this::getOtherMetadata);
        uaMetadataMap.put(INOSEnum.DARWIN,this::getOtherMetadata);

    }

    @Override
    public UserAgentInfo getUserAgentInfoByUserAgent(String UserAgent){
        final Set<Map.Entry<String, BiFunction<Matcher, String, UserAgentInfo>>> entries = userAgentMap.entrySet();
        for (Map.Entry<String, BiFunction<Matcher, String, UserAgentInfo>> entry:entries) {
            Pattern regexPattern = Pattern.compile(entry.getKey());
            Matcher matcher = regexPattern.matcher(UserAgent);
            if (matcher.find()) {
                return entry.getValue().apply(matcher, UserAgent);
            }
        }
        return null;
    }

    @Override
    public OpenUserAgentMetadata getOpenUserAgentMetadata(UserAgentInfo userAgentInfo){

        final Function<UserAgentInfo, OpenUserAgentMetadata> func = uaMetadataMap.get(userAgentInfo.getOs());
        if(Objects.isNull(func)){
            return null;
        }
        final OpenUserAgentMetadata apply = func.apply(userAgentInfo);
        return apply;
    }



    private UserAgentInfo getWinUserAgent(Matcher matcher,String ua){

        String osVersion = matcher.group(1);
        String osArchitecture = matcher.group(2);
        String chromeVersion = matcher.group(3);

        UserAgentInfo userAgentInfo = new UserAgentInfo();


        userAgentInfo.setMobile(Boolean.FALSE);
        userAgentInfo.setPlatform(PlatformEnum.WINDOWS);

        PlatformVersionEnum platformVersion = switch (osVersion) {
            case "10.0" -> PlatformVersionEnum.V10_0_0;
            default -> PlatformVersionEnum.V10_0_0;
        };
        userAgentInfo.setPlatformVersion(platformVersion);

        userAgentInfo.setArchitecture(ArchitectureEnum.X86);

        if(StringUtils.isBlank(osArchitecture)){
            userAgentInfo.setBitness(BitnessEnum.x32);
        }else{
            userAgentInfo.setBitness(BitnessEnum.x64);
        }


        if(osArchitecture.indexOf("wow64")>-1){
            userAgentInfo.setWow64(Boolean.TRUE);
        }else{
            userAgentInfo.setWow64(Boolean.FALSE);
        }

        userAgentInfo.setMobile(null);

        userAgentInfo.setUaFullVersion(chromeVersion);

        OsEnum os=switch (userAgentInfo.getPlatformVersion()){
            case V10_0_0 -> OsEnum.WIN10;
            case V6_1 -> OsEnum.WIN7;
            default -> OsEnum.WIN10;
        };


        userAgentInfo.setOsVersion(os);
        userAgentInfo.setOs(INOSEnum.WIN32);
        userAgentInfo.setOsArchitecture(osArchitecture);
        userAgentInfo.setChromeVersion(userAgentInfo.getUaFullVersion());

        userAgentInfo.setShowVersion(convertVersion(chromeVersion));

        userAgentInfo.setShowUserAgent(ua.replace(chromeVersion,userAgentInfo.getShowVersion()));

        userAgentInfo.setUaMajorVersion(convertMajorVersion(chromeVersion));

        return userAgentInfo;
    }

    private static Integer convertMajorVersion(String originalVersion) {
        // 使用正则表达式匹配版本号中的数字部分
        String[] parts = originalVersion.split("\\.");
        // 确保至少有四个部分
        if (parts.length < 4) {
            throw new IllegalArgumentException("无法将版本号转换为目标格式。");
        }
        return Integer.parseInt(parts[0]);
    }

    private static String convertVersion(String originalVersion) {
        // 使用正则表达式匹配版本号中的数字部分
        String[] parts = originalVersion.split("\\.");
        // 确保至少有四个部分
        if (parts.length < 4) {
            throw new IllegalArgumentException("无法将版本号转换为目标格式。");
        }
        // 将234 分设置为0
        parts[1] = "0";
        parts[2] = "0";
        parts[3] = "0";

        // 将部分重新组合为新版本号
        String convertedVersion = String.join(".", parts);
        return convertedVersion;
    }



    private  OpenUserAgentMetadata getWinUaMetadata(UserAgentInfo userAgentInfo){

        final UserAgentMetadataInfo userAgentMetadataInfo = userAgentMetadataInfoRepository.findByUaMajorVersion(userAgentInfo.getUaMajorVersion());

        OpenUserAgentMetadata uaMetadata = new OpenUserAgentMetadata();
        uaMetadata.setBrandVersionList(userAgentMetadataInfo.getBrands());
        uaMetadata.setMobile(false);
        uaMetadata.setPlatform(userAgentInfo.getPlatform());
        uaMetadata.setPlatformVersion(userAgentInfo.getPlatformVersion());
        uaMetadata.setArchitecture(userAgentInfo.getArchitecture());
        uaMetadata.setBitness(userAgentInfo.getBitness());
        uaMetadata.setWow64(userAgentInfo.getWow64());
        uaMetadata.setModel(userAgentInfo.getModel());
        uaMetadata.setFullVersion(userAgentInfo.getChromeVersion());

        userAgentMetadataInfo.getFullVersionList().forEach(brandVersion->{
            if(brandVersion.getVersion().equals(userAgentInfo.getShowVersion())){
                brandVersion.setVersion(userAgentInfo.getChromeVersion());
            }
        });
        uaMetadata.setBrandFullVersionList(userAgentMetadataInfo.getFullVersionList());

        return uaMetadata;

    }

    private OpenUserAgentMetadata getOtherMetadata(UserAgentInfo userAgentInfo){
        return null;
    }


}
