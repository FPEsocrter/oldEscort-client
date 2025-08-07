package cn.escort.web.business.font.service;


import cn.escort.launcher.baseLauncher.ExeLauncher;
import cn.escort.web.business.fingerprint.domain.openFingerprint.FontInfo;
import cn.escort.web.business.font.domain.Font;
import cn.escort.web.business.font.domain.FontCountry;
import cn.escort.web.business.font.domain.FontFamilyInfo;
import cn.escort.web.business.font.repository.FontCountryRepository;
import cn.escort.web.business.font.repository.FontRepository;
import cn.escort.web.business.userAgent.domain.OsEnum;
import cn.escort.web.business.userAgent.domain.UserAgentInfo;

import cn.escort.web.business.userAgent.service.UserAgentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FontServiceImpl implements FontService{

    private final FontRepository fontRepository;

    private final FontCountryRepository fontCountryRepository;

    private final UserAgentService userAgentService;

    private String exePath="..\\env\\systemFontList.exe";

    private List<FontFamilyInfo> list;

    @PostConstruct
    public void init(){
        run();
    }

    @Override
    public List<String> getFontListAll() {
        return fontRepository.findAll().stream().map(Font::getName).map(String::toLowerCase).collect(Collectors.toList());
    }

    @Override
    public List<String> getRandFontList(String userAgent) {
        final UserAgentInfo userAgentInfoByUserAgent = userAgentService.getUserAgentInfoByUserAgent(userAgent);
        return getRandFontList(userAgentInfoByUserAgent).stream().map(String::toLowerCase).collect(Collectors.toList());

    }

    public List<String> getFontList(String userAgent,String code){
        return getFontList(userAgentService.getUserAgentInfoByUserAgent(userAgent),code);
    }
    public List<String> getFontList(UserAgentInfo userAgentInfo,String code){
        final OsEnum win10 = OsEnum.WIN10;
        final FontCountry byOsAndCountryCodeCustom = fontCountryRepository.findByOsAndCountryCode(win10, "template");
        final FontCountry fontCountry = fontCountryRepository.findByOsAndCountryCode(win10, code);
        final List<String> fontList = byOsAndCountryCodeCustom.getFontList();
        if(Objects.nonNull(fontCountry)){
            fontList.addAll(fontCountry.getFontList());
        }

        return fontList.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    @Override
    public List<String> getRandFontList(UserAgentInfo userAgentInfo) {

        final OsEnum win10 = OsEnum.WIN10;//userAgentInfo.getOsVersion();
        String  countryCode="template"+userAgentInfo.getOsVersion().name();
        countryCode = Character.toUpperCase(countryCode.charAt(0)) + countryCode.substring(1);
        final FontCountry byOsAndCountryCodeCustom = fontCountryRepository.findByOsAndCountryCode(win10, countryCode);
        final List<String> fontList = byOsAndCountryCodeCustom.getFontList();

        return getRandomFont(fontList, 25, 35).stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public  List<String> getRandomFont(List<String> removeList, int minCount, int maxCount) {
        final List<Font> list = fontRepository.findAll();
        final List<String> collect = list.stream()
                .filter(font -> font.getInOs().isEmpty())
                .map(Font::getName).collect(Collectors.toList());
        collect.removeAll(removeList);
        List<String> randomElements = new ArrayList<>();
        Random random = new Random();

        int count = random.nextInt(maxCount - minCount + 1) + minCount; // 随机获取数量

        Collections.shuffle(collect); // 随机打乱原始列表

        for (int i = 0; i < count; i++) {
            randomElements.add(collect.get(i));
        }

        return randomElements;

    }


    @Override
    public Map<String, FontInfo> getFontMap(List<String> fontList) {
        LinkedHashMap<String, FontInfo> fontMap = new LinkedHashMap<>();
        Random random = new Random();
        fontList.forEach(fontName->{
            FontInfo fontInfo = new FontInfo();
            fontInfo.setId(0L);
            fontInfo.setWidth(0.7 + random.nextDouble() * 1.0);
            fontInfo.setHeight(0.7 + random.nextDouble() * 1.0);

            fontInfo.setActualBoundingBoxAscent(0D);
            fontInfo.setActualBoundingBoxDescent(0D);
            fontInfo.setActualBoundingBoxLeft(0D);
            fontInfo.setActualBoundingBoxRight(0D);
            fontInfo.setFontBoundingBoxAscent(0D);
            fontInfo.setFontBoundingBoxDescent(0D);
            fontInfo.setFilePaths(new ArrayList<>());
            Consumer<Double> consumer =switch (random.nextInt(6)){
                case 1->fontInfo::setActualBoundingBoxDescent;
                case 2->fontInfo::setActualBoundingBoxLeft;
                case 3->fontInfo::setActualBoundingBoxRight;
                case 4->fontInfo::setFontBoundingBoxAscent;
                case 5->fontInfo::setFontBoundingBoxDescent;
                default -> fontInfo::setActualBoundingBoxAscent;
            };
            consumer.accept( random.nextDouble() * 5+0.0000001D);

            fontMap.put(fontName,fontInfo);
        });
        return fontMap;
    }

    public List<FontFamilyInfo> getSystemFontList(){
        if(Objects.isNull(list)){
            run();
        }
        return list;
    }

    @RegisterReflectionForBinding(FontFamilyInfo.class)//graalvm
    private void run(){
        //todo 改成完整路径
        final ExeLauncher exeLauncher = new ExeLauncher(exePath);
        exeLauncher.run();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String str= exeLauncher.readString(5);
            list = objectMapper.readValue(str, new TypeReference<List<FontFamilyInfo>>() { });
            list.forEach(fontFamilyInfo->{

                final HashSet<String> all = new HashSet<>(fontFamilyInfo.getFullNames());
                all.addAll(fontFamilyInfo.getFamily());
                fontFamilyInfo.setFullNames(all.stream().map(String::toLowerCase).collect(Collectors.toList()));
            });
            log.debug("获取系统字体{}", list);
        } catch ( ExecutionException | InterruptedException | TimeoutException | RuntimeException |
                  JsonProcessingException e) {
            log.error("字体初始异常",e);
        }
    }

}
