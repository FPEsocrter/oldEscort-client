package cn.escort.web.business.chromiumData.controller;

import cn.escort.web.business.chromiumData.service.ChromiumDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "环境数据接口", description = "环境数据接口")
@RestController
@RequestMapping("/chromiumData")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChromiumDataController {

    private final ChromiumDataService chromiumDataService;



}
