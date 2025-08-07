package cn.escort.web.business.environment.service;

import cn.escort.frameworkConfig.web.entity.Page;
import cn.escort.web.business.environment.domain.dto.*;
import cn.escort.web.business.environment.domain.vo.EnvironmentByIdVo;
import cn.escort.web.business.environment.domain.vo.EnvironmentPageVo;

import java.util.List;


public interface EnvironmentService {

    Boolean add(AddEnvironmentDto addEnvironmentReq);

    Boolean delete(List<Long> ids);

    Boolean modify(ModifyEnvironmentDto modifyEnvironmentReq);

    EnvironmentByIdVo getById(ByIdEnvironmentDto byIdEnvironmentReq);

    Page<EnvironmentPageVo> page(ListEnvironmentDto listEnvironmentReq);


}
