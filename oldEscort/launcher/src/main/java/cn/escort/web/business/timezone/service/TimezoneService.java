package cn.escort.web.business.timezone.service;

import cn.escort.web.business.timezone.domain.vo.TimezoneVo;

import java.util.List;

public interface TimezoneService {

    List<TimezoneVo> getTimezoneList();
}
