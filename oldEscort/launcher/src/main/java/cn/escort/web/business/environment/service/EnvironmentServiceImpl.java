package cn.escort.web.business.environment.service;

import cn.escort.frameworkConfig.web.entity.DeletedEnum;
import cn.escort.frameworkConfig.web.entity.Page;
import cn.escort.launcher.Launcher;
import cn.escort.web.business.chromiumData.domain.ChromiumData;
import cn.escort.web.business.chromiumData.domain.dto.ChromiumDataDto;
import cn.escort.web.business.chromiumData.repository.ChromiumDataRepository;
import cn.escort.web.business.chromiumData.service.ChromiumDataService;
import cn.escort.web.business.environment.domain.Environment;
import cn.escort.web.business.environment.domain.WebProxy;
import cn.escort.web.business.environment.domain.dto.*;
import cn.escort.web.business.environment.domain.vo.*;
import cn.escort.web.business.environment.repository.EnvironmentSqlRepository;
import cn.escort.web.business.fingerprint.domain.Fingerprint;
import cn.escort.web.business.fingerprint.domain.SimpleFingerprint;
import cn.escort.web.business.fingerprint.repository.FingerprintRepository;
import cn.escort.web.business.fingerprint.service.FingerprintService;
import cn.escort.web.business.launcher.service.ChromiumLauncherMapService;
import cn.escort.web.business.webProxy.domain.WebProxyColEnum;
import cn.escort.web.business.webProxy.service.WebProxyService;
import lombok.RequiredArgsConstructor;
import cn.escort.web.business.environment.repository.EnvironmentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.time.Instant;


//@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnvironmentServiceImpl implements EnvironmentService {

    private final EnvironmentRepository environmentRepository;

    private final FingerprintRepository fingerprintRepository;

    private final EnvironmentSqlRepository environmentSqlRepository;

    private final ChromiumDataRepository chromiumDataRepository;

    private final FingerprintService fingerprintService;

    private final ChromiumDataService chromiumDataService;

    private final WebProxyService webProxyService;

    private final ChromiumLauncherMapService chromiumLauncherMapService;


    @Override
    public Boolean add(AddEnvironmentDto addEnvironmentReq) {


        EnvironmentDto environmentDto = addEnvironmentReq.getEnvironment();

        Environment environment = EnvironmentReqAndWebProxyConvert(environmentDto, addEnvironmentReq.getWebProxy());
        environmentRepository.save(environment);

        Fingerprint fingerprint = fingerprintService.addConvert(environment.getId(), addEnvironmentReq.getFingerprint(), environmentDto.getUserAgent());
        fingerprintRepository.save(fingerprint);


        ChromiumDataDto chromiumDataDto = new ChromiumDataDto();
        chromiumDataDto.setCookie(environmentDto.getCookie());
        ChromiumData chromiumData = chromiumDataService.addConvert(environment.getId(), environment.getName(), chromiumDataDto);
        chromiumDataRepository.save(chromiumData);

        return Boolean.TRUE;
    }


    @Transactional
    @Override
    public Boolean delete(List<Long> ids) {
        ids.forEach(id -> {
            environmentRepository.deleteByIdWithLogic(id);

            Fingerprint byEnvironmentId = fingerprintRepository.getByEnvironmentId(id);
            fingerprintRepository.deleteByIdWithLogic(byEnvironmentId.getId());

            ChromiumData chromiumData = chromiumDataRepository.getByEnvironmentId(id);
            fingerprintRepository.deleteByIdWithLogic(chromiumData.getEnvironmentId());

        });
        return Boolean.TRUE;
    }

    @Override
    public Boolean modify(ModifyEnvironmentDto modifyEnvironmentReq) {

        Boolean flag = Boolean.FALSE;
        Environment oneWithLogic = environmentRepository.getOneWithLogic(modifyEnvironmentReq.getId());
        if (Objects.isNull(oneWithLogic)) {
            return false;
        }

        EnvironmentDto environmentDto = modifyEnvironmentReq.getEnvironment();

        if (Objects.nonNull(environmentDto) || Objects.nonNull(modifyEnvironmentReq.getWebProxy())) {
            EnvironmentReqAndWebProxyConvert(environmentDto, modifyEnvironmentReq.getWebProxy(), oneWithLogic);
            environmentRepository.save(oneWithLogic);

            flag = Boolean.TRUE;
        }
        boolean userAgentFlag = Objects.nonNull(environmentDto) && StringUtils.isNotBlank(environmentDto.getUserAgent());
        boolean fingerprintFlag = Objects.nonNull(modifyEnvironmentReq.getFingerprint()) || userAgentFlag;

        if (fingerprintFlag) {
            Fingerprint fingerprint = fingerprintRepository.getByEnvironmentId(modifyEnvironmentReq.getId());
            String userAgent = fingerprint.getAddFingerprint().getUserAgent();
            if (userAgentFlag) {
                userAgent = environmentDto.getUserAgent();
            }
            fingerprintService.updateConvert(modifyEnvironmentReq.getFingerprint(), userAgent, fingerprint);
            flag = Boolean.TRUE;
            fingerprintRepository.save(fingerprint);
        }

        if (Objects.nonNull(environmentDto) && Objects.nonNull(environmentDto.getCookie()) && !environmentDto.getCookie().isEmpty()) {
            ChromiumData chromiumData = chromiumDataRepository.getByEnvironmentId(modifyEnvironmentReq.getId());
            ChromiumDataDto chromiumDataDto = new ChromiumDataDto();
            chromiumDataDto.setCookie(environmentDto.getCookie());
            chromiumData = chromiumDataService.updateConvert(chromiumData, chromiumDataDto);
            flag = Boolean.TRUE;
            chromiumDataRepository.save(chromiumData);
        }

        return flag;
    }


    @Override
    public EnvironmentByIdVo getById(ByIdEnvironmentDto dto) {

        EnvironmentByIdVo fingerprintByIdVo = new EnvironmentByIdVo();

        boolean all = Objects.isNull(dto.getEnvironment()) &&
                Objects.isNull(dto.getWebProxy()) &&
                Objects.isNull(dto.getFingerprint());


        Environment oneWithLogic = environmentRepository.getOneWithLogic(dto.getId());
        if (Objects.isNull(oneWithLogic)) {
            return fingerprintByIdVo;
        } else {
            fingerprintByIdVo.setId(dto.getId());
        }

        if (all || Boolean.TRUE.equals(dto.getEnvironment())) {
            //设置环境标准信息
            EnvironmentDto environmentDto = new EnvironmentDto();
            BeanUtils.copyProperties(oneWithLogic, environmentDto);
            //cookie
            ChromiumDataDto chromiumDataDto = chromiumDataService.getDtoById(dto.getId());
            environmentDto.setCookie(chromiumDataDto.getCookie());
            //userAgent
            Fingerprint fingerprint = fingerprintRepository.getByEnvironmentId(dto.getId());
            String userAgent = fingerprint.getAddFingerprint().getUserAgent();
            environmentDto.setUserAgent(userAgent);

            fingerprintByIdVo.setEnvironment(environmentDto);
        }

        if (all || Boolean.TRUE.equals(dto.getWebProxy())) {
            WebProxyDto webProxyDto = new WebProxyDto();
            Map<String, String> proxyInfo = oneWithLogic.getProxyInfo();
            WebProxy webProxy = webProxyService.getWebProxy(proxyInfo);
            BeanUtils.copyProperties(webProxy, webProxyDto);
            fingerprintByIdVo.setWebProxy(webProxyDto);
        }

        if (all ||  Boolean.TRUE.equals(dto.getFingerprint())) {

            FingerprintDto fingerprintDto = new FingerprintDto();

            Fingerprint environment = fingerprintRepository.getByEnvironmentId(dto.getId());
            SimpleFingerprint addFingerprint = environment.getAddFingerprint();
            BeanUtils.copyProperties(addFingerprint, fingerprintDto);

            fingerprintByIdVo.setFingerprint(fingerprintDto);
        }
        return fingerprintByIdVo;
    }

    @Override
    public Page<EnvironmentPageVo> page(ListEnvironmentDto listEnvironmentReq) {
        Page<EnvironmentPageVo> environmentPageVoPage = environmentSqlRepository.listPage(listEnvironmentReq, listEnvironmentReq.getPage());
        if(Objects.isNull(environmentPageVoPage.getList())||environmentPageVoPage.getList().isEmpty()){
            return environmentPageVoPage;
        }
        Map<Long, Launcher> chromiumLauncherMap = chromiumLauncherMapService.getChromiumLauncherMap();
        environmentPageVoPage.getList().forEach(vo->{
            if(chromiumLauncherMap.containsKey(vo.getId())){
                vo.setOpen(Boolean.FALSE);
            }else{
                vo.setOpen(Boolean.TRUE);
            }
        });
        return environmentPageVoPage;

    }

    private Environment EnvironmentReqAndWebProxyConvert(EnvironmentDto environmentReq, WebProxyDto webProxyReq) {
        return EnvironmentReqAndWebProxyConvert(environmentReq, webProxyReq, null);
    }

    private Environment EnvironmentReqAndWebProxyConvert(EnvironmentDto environmentReq, WebProxyDto webProxyReq, Environment environment) {
        if (Objects.isNull(environment)) {
            environment = new Environment();

            long epochMilliNew = Instant.now().toEpochMilli();
            LocalDateTime localDateTime = LocalDate.of(2023, 10, 5).atStartOfDay();
            long epochMilli2023105 = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();


            environment.setSerialNumber((epochMilliNew - epochMilli2023105) / 10 + 10000000);
            environment.setDeleted(DeletedEnum.NOT_DELETED);
        }
        env:
        {
            if (Objects.nonNull(environmentReq)) {
                environment.setName(environmentReq.getName());
                environment.setRemark(environmentReq.getRemark());
            }

        }
        proxy:
        {
            if (Objects.nonNull(webProxyReq)) {
                environment.setWebProxy(webProxyReq.getType());
                Map<String, String> info = webProxyReq.getOther();
                info.put(WebProxyColEnum.TYPE.getName(), String.valueOf(webProxyReq.getType().ordinal()));
                info.put(WebProxyColEnum.HOST.getName(), webProxyReq.getHost());
                if (Objects.nonNull(webProxyReq.getPort())) {
                    info.put(WebProxyColEnum.PORT.getName(), String.valueOf(webProxyReq.getPort()));
                }
                info.put(WebProxyColEnum.ACCOUNT.getName(), webProxyReq.getAccount());
                info.put(WebProxyColEnum.PASSWORD.getName(), webProxyReq.getPassword());

                environment.setProxyInfo(info);
            }

        }

        return environment;
    }


}
