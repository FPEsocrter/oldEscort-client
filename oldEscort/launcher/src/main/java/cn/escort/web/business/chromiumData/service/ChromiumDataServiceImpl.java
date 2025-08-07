package cn.escort.web.business.chromiumData.service;

import cn.escort.frameworkConfig.web.entity.DeletedEnum;
import cn.escort.web.business.chromiumData.domain.ChromiumData;
import cn.escort.web.business.chromiumData.domain.dto.ChromiumDataDto;
import cn.escort.web.business.chromiumData.repository.ChromiumDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChromiumDataServiceImpl implements ChromiumDataService{

    private final ChromiumDataRepository chromiumDataRepository;
    @Override
    public ChromiumData addConvert(Long id, String evnName, ChromiumDataDto dto) {
        ChromiumData chromiumData = new ChromiumData();
        chromiumData.setEnvironmentId(id);
        final  long time = System.currentTimeMillis();
        String name = id.toString() + time+evnName;
        String pathName = DigestUtils.md5DigestAsHex(name.getBytes(StandardCharsets.UTF_8))+Long.toHexString(time);
        chromiumData.setPath(pathName);
        chromiumData.setCookiePath(saveJsonCookieWithPath(dto.getCookie()));
        chromiumData.setDeleted(DeletedEnum.NOT_DELETED);
        return chromiumData;
    }

    @Override
    public ChromiumData updateConvert(ChromiumData chromiumData, ChromiumDataDto dto) {
        if(Objects.nonNull(dto.getCookie())&&!dto.getCookie().isEmpty()){
            chromiumData.setCookiePath(saveJsonCookieWithPath(dto.getCookie()));
        }
        return chromiumData;
    }

    public ChromiumDataDto getDtoById(Long id){
        ChromiumDataDto dto=new ChromiumDataDto();
        ChromiumData chromiumData = chromiumDataRepository.getByEnvironmentId(id);
        String cookiePath = chromiumData.getCookiePath();
        List<Map<String, String>> cookie = pathToJsonCookie(cookiePath);
        dto.setCookie(cookie);
        return dto;
    }


    private String saveJsonCookieWithPath(List<Map<String, String>> cookie) {
        //todo
        return "";
    }


    private List<Map<String, String>> pathToJsonCookie(String path) {
        //todo
        return new ArrayList<>();
    }


}
