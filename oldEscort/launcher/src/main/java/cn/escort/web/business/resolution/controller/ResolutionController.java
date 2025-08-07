package cn.escort.web.business.resolution.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "分辨率接口", description = "分辨率相关接口")
@RestController
@RequestMapping("/resolution")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResolutionController {

}
