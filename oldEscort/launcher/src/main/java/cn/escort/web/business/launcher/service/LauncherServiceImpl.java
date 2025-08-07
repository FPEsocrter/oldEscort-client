package cn.escort.web.business.launcher.service;

import cn.escort.frameworkConfig.web.exceptionHandler.exception.BadServiceException;
import cn.escort.frameworkConfig.web.exceptionHandler.exception.ExceptionUtil;
import cn.escort.launcher.Launcher;
import cn.escort.launcher.chromiumLauncher.AipKey;
import cn.escort.launcher.networkDetectionLauncher.IpDetails;
import cn.escort.launcher.networkDetectionLauncher.JPythonNetworkDetectionLauncher;
import cn.escort.launcher.webProxyLauncher.InetAddress;
import cn.escort.launcher.webProxyLauncher.WebProxyParameters;
import cn.escort.web.business.chromiumData.domain.ChromiumData;
import cn.escort.web.business.chromiumData.repository.ChromiumDataRepository;
import cn.escort.web.business.environment.domain.Environment;
import cn.escort.web.business.environment.domain.WebProxy;
import cn.escort.web.business.environment.repository.EnvironmentRepository;
import cn.escort.web.business.fingerprint.domain.Fingerprint;
import cn.escort.web.business.fingerprint.domain.OpenFingerprint;
import cn.escort.web.business.fingerprint.domain.openFingerprint.*;
import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import cn.escort.web.business.fingerprint.repository.FingerprintRepository;
import cn.escort.web.business.font.domain.FontFamilyInfo;
import cn.escort.web.business.font.service.FontService;
import cn.escort.web.business.language.domain.SimpleLang;
import cn.escort.web.business.language.service.LangService;
import cn.escort.web.business.launcher.domain.OpenFingerprintDto;
import cn.escort.web.business.speechVoices.service.SpeechVoiceCountryService;
import cn.escort.web.business.userAgent.domain.OsEnum;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import cn.escort.web.business.webProxy.service.WebProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LauncherServiceImpl implements LauncherService {

    private final ChromiumLauncherMapService chromiumLauncherMapService;

    private final EnvironmentRepository environmentRepository;

    private final FingerprintRepository fingerprintRepository;

    private final ChromiumDataRepository chromiumDataRepository;

    private final WebProxyService webProxyService;

    private final SpeechVoiceCountryService speechVoiceCountryService;

    private final LangService languageService;

    private final org.springframework.core.env.Environment environment;

    private final FontService fontService;

    private int getServerPort() {
        return Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port")));
    }


    public String getLangByCountryCode(String CountryCode) {
        final List<SimpleLang> langList = languageService.getLanguageByCountry(CountryCode);
        for (SimpleLang simpleLang : langList) {
            if (simpleLang.getSupportsUI()) {
                return simpleLang.getCode();
            }
        }
        return langList.get(0).getCode();
    }

    @Override
    @RegisterReflectionForBinding({AipKey.class, InetAddress.class,IpDetails.class,
            WebProxy.class,

            WebProxyParameters.class,WebProxyEnum.class})
    public Boolean launcherChromium(List<Long> ids) {
        ids.forEach(id -> {
            if (getChromiumLauncherMap().containsKey(id)) {
                throw new BadServiceException("环境已启动不需要重复启动,后续优化中");
            }
            final Environment oneWithLogic = environmentRepository.getOneWithLogic(id);
            ExceptionUtil.requireNonNull(oneWithLogic, "环境不存在");

            final Fingerprint byEnvironmentId = fingerprintRepository.getByEnvironmentId(id);

            OpenFingerprint openFingerprint = byEnvironmentId.getStableOpenFingerprint();
            if (Objects.isNull(openFingerprint)) {
                openFingerprint = byEnvironmentId.getOpenFingerprint();
            }
            final ChromiumData chromiumData = chromiumDataRepository.getByEnvironmentId(id);
            //查询出来id 一致的数据会联动
            WebProxy webProxy = webProxyService.getWebProxy(new HashMap<>(oneWithLogic.getProxyInfo()));

            Launcher launcher = new Launcher(id, getServerPort(), openFingerprint, webProxy, chromiumData, this::getLangByCountryCode);

            launcher.run();
            getChromiumLauncherMap().put(id, launcher);
            new Thread(() -> {
                final IpDetails ipInfo = launcher.getJPythonNetworkDetectionLauncher().getCacheIpInfo();
                if(Objects.isNull(ipInfo)){
                    return;
                }
                oneWithLogic.setLastUseIp(String.join(",", ipInfo.getIps()));
                oneWithLogic.setArea(ipInfo.getCountryCode() + " " + ipInfo.getRegion() + ipInfo.getCity());
                environmentRepository.save(oneWithLogic);
            }).start();
        });
        final List<Environment> allByIdWithLogic = environmentRepository.findAllByIdWithLogic(ids);
        allByIdWithLogic.forEach(obj -> {
            obj.setLastOpenTime(LocalDateTime.now());
        });
        environmentRepository.saveAll(allByIdWithLogic);
        return Boolean.TRUE;
    }

    @Override
    public OpenFingerprint getOpenFingerprint(OpenFingerprintDto dto) {
        final Long id = dto.getId();
        final Environment oneWithLogic = environmentRepository.getOneWithLogic(id);
        ExceptionUtil.requireNonNull(oneWithLogic, "环境不存在");

        Launcher launcher = getChromiumLauncherMap().get(id);
        if (Objects.isNull(launcher)) {
            return null;
        }
        OpenFingerprint openFingerprint = launcher.getOpenFingerprint();
        if (verifyFollowIp(openFingerprint)) {
            final JPythonNetworkDetectionLauncher jPythonNetworkDetectionLauncher = launcher.getJPythonNetworkDetectionLauncher();
            final IpDetails ipInfo = jPythonNetworkDetectionLauncher.getCacheIpInfo();
            openFingerprintCustomizeByIpDetails(openFingerprint, ipInfo);
        }
        final OpenFont openFont = openFingerprint.getFont();
        if (!OpenCustomizeTypeEnum.TURN_OFF.equals(openFont.getType())) {
            final List<FontFamilyInfo> systemFontList = fontService.getSystemFontList();
            Set<String> allFullNames = systemFontList.stream()
                    .map(FontFamilyInfo::getFullNames)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet());

            final Map<String, FontInfo> fontMap = openFont.getFontMap();
            AtomicLong index = new AtomicLong(Long.valueOf(systemFontList.size() + 1));
            fontMap.forEach((fontName, fontInfo) -> {
                if (allFullNames.contains(fontName)) {
                    fontInfo.setId(-1L);
                } else {
                    fontInfo.setId(Long.valueOf(index.get()));
                    index.getAndIncrement();
                }
            });
        }
        return openFingerprint;
    }

    @Override
    public Boolean closeChromium(Long id) {
        Launcher launcher = getChromiumLauncherMap().get(id);
        launcher.getChromiumLauncher().kill();
        if (Objects.nonNull(launcher.getJPythonNetworkDetectionLauncher())) {
            launcher.getJPythonNetworkDetectionLauncher().kill();
        }
        if (Objects.nonNull(launcher.getWebProxyLauncher())) {
            launcher.getWebProxyLauncher().kill();
        }
        getChromiumLauncherMap().remove(id);
        return Boolean.TRUE;
    }

    @Scheduled(fixedRate = 500)
    public void verifyCoreProcess() {
        final ArrayList<Long> noAlive = new ArrayList<>();
        final Map<Long, Launcher> chromiumLauncherMap = getChromiumLauncherMap();
        chromiumLauncherMap.forEach((key, value) -> {
            if (Objects.isNull(value)||Objects.isNull(value.getChromiumLauncher())||Objects.isNull(value.getChromiumLauncher().getProcess())) {
                noAlive.add(key);
                return;
            }
            if (!value.getChromiumLauncher().getProcess().isAlive()) {
                closeChromium(key);
                noAlive.add(key);
            }
        });
        noAlive.forEach(chromiumLauncherMap::remove);
    }

    private Map<Long, Launcher> getChromiumLauncherMap() {
        return chromiumLauncherMapService.getChromiumLauncherMap();
    }

    private Boolean verifyFollowIp(OpenFingerprint openFingerprint) {
        timeZone:
        {
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(openFingerprint.getTimeZone().getType())) {
                return Boolean.TRUE;
            }
        }

        webrtc:
        {
            final OpenWebRTC webRTC = openFingerprint.getWebRTC();
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(webRTC.getType())) {
                return Boolean.TRUE;
            }
        }

        location:
        {
            final OpenLocation location = openFingerprint.getLocation();
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(location.getType())) {

            }

        }
        language:
        {
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(openFingerprint.getLanguage().getType())) {
                return Boolean.TRUE;
            }
        }
        font:
        {
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(openFingerprint.getFont().getType())) {
                return Boolean.TRUE;
            }
        }
        speechVoices:
        {
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(openFingerprint.getSpeechVoices().getType())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private void openFingerprintCustomizeByIpDetails(OpenFingerprint openFingerprint, IpDetails ipInfo) {
        timeZone:
        {
            final OpenTimeZone timeZone = openFingerprint.getTimeZone();
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(timeZone.getType())) {
                timeZone.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                timeZone.setGmt(ipInfo.getTimezone());
            }
        }

        webrtc:
        {
            final OpenWebRTC webRTC = openFingerprint.getWebRTC();
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(webRTC.getType())) {
                webRTC.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                webRTC.setPublicIp(ipInfo.getIps().get(0));
            }
        }
        location:
        {
            final OpenLocation location = openFingerprint.getLocation();
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(location.getType())) {
                location.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                location.setLongitude(ipInfo.getLongitude());
                location.setLatitude(ipInfo.getLatitude());
                location.setAccuracy(1000F);
            }

        }
        language:
        {
            final OpenLanguage language = openFingerprint.getLanguage();
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(language.getType())) {
                final List<SimpleLang> languageByCountry = languageService.getLanguageByCountry(ipInfo.getCountryCode());
                final List<String> languages = languageByCountry.stream().map(SimpleLang::getCode).collect(Collectors.toList());
                for (int i = 0; i < languageByCountry.size(); i++) {
                    final SimpleLang simpleLang = languageByCountry.get(i);
                    if (simpleLang.getSupportsUI()) {
                        language.setInterfaceLanguage(simpleLang.getCode());
                        break;
                    }
                }
                language.setLanguages(languages);
                language.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
            }
        }
        font:
        {
            final OpenFont font = openFingerprint.getFont();
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(font.getType())) {
                final List<String> randFontList = fontService.getFontList(openFingerprint.getUa().getUserAgent(), ipInfo.getCountryCode());
                font.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                font.setFontMap(fontService.getFontMap(randFontList));
            }
        }
        speechVoices:
        {
            final OpenSpeechVoices speechVoices = openFingerprint.getSpeechVoices();
            if (OpenCustomizeTypeEnum.FOLLOW_IP.equals(speechVoices.getType())) {
                speechVoices.setType(OpenCustomizeTypeEnum.CUSTOMIZE);
                speechVoices.setList(speechVoiceCountryService.getSpeechVoiceByOsAndLang(OsEnum.WIN10, ipInfo.getCountryCode()));
            }
        }
    }

}
