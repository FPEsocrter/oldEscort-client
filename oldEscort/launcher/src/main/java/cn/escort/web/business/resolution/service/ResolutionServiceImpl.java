package cn.escort.web.business.resolution.service;

import cn.escort.web.business.resolution.domain.vo.ListResolutionLVo;
import cn.escort.web.business.resolution.repository.ResolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResolutionServiceImpl implements ResolutionService{

    private final ResolutionRepository resolutionRepository;

    @Override
    public List<ListResolutionLVo> getResolutionList() {
        return resolutionRepository.findAll().stream().map(vo -> {
            ListResolutionLVo listResolutionLVo = new ListResolutionLVo();
            listResolutionLVo.setWindowHeight(vo.getHeight());
            listResolutionLVo.setWindowWidth(vo.getWidth());
            return listResolutionLVo;
        }).collect(Collectors.toList());
    }

}
