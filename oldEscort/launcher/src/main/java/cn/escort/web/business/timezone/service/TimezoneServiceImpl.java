package cn.escort.web.business.timezone.service;

import cn.escort.web.business.timezone.domain.Timezone;
import cn.escort.web.business.timezone.domain.vo.TimezoneVo;
import cn.escort.web.business.timezone.repository.TimezoneRepository;
import cn.escort.web.business.userAgent.repository.UaVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TimezoneServiceImpl implements TimezoneService {

    private final TimezoneRepository timezoneRepository;

    @Override
    public List<TimezoneVo> getTimezoneList() {
       return timezoneRepository.findAll().stream().map(TimezoneVo::Timezone).collect(Collectors.toList());
    }


}
