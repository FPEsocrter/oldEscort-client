package cn.escort.web.business.webProxy.controller;

import cn.escort.frameworkConfig.web.entity.JsonResult;
import cn.escort.web.business.environment.domain.dto.WebProxyDto;
import cn.escort.web.business.webProxy.domain.IpInfo;
import cn.escort.web.business.webProxy.service.WebProxyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "网络代理接口", description = "网络代理接口")
@RestController
@RequestMapping("/webProxy")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebProxyController {

    private final WebProxyService webProxyService;

    @Operation(summary = "检测代理",tags = {"v0.0.1"})
    @PostMapping("/detection")
    public JsonResult<IpInfo> detection(@RequestBody @Valid WebProxyDto webProxy) {

        return JsonResult.success(webProxyService.detection(webProxy));
    }

}
