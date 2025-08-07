package cn.escort.web.business.chromiumData.service;

import cn.escort.web.business.chromiumData.domain.ChromiumData;
import cn.escort.web.business.chromiumData.domain.dto.ChromiumDataDto;

import java.util.Map;

public interface ChromiumDataService {

    ChromiumData addConvert(Long id, String evnName, ChromiumDataDto dto);

    ChromiumData updateConvert(ChromiumData chromiumData, ChromiumDataDto dto);

    ChromiumDataDto getDtoById(Long id);
}
