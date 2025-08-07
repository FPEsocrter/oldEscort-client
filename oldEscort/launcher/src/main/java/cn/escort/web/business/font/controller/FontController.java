package cn.escort.web.business.font.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "字体接口", description = "字体相关接口")
@RestController
@RequestMapping("/font")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FontController {


}
