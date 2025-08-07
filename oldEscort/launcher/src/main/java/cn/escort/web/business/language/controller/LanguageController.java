package cn.escort.web.business.language.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "指纹语言接口", description = "指纹语言相关接口")
@RestController
@RequestMapping("/language")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LanguageController {


}
