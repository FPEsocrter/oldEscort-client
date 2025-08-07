package cn.escort.web.business.launcher.controller;

import cn.escort.frameworkConfig.web.decrypt.Decrypt;
import cn.escort.frameworkConfig.web.encrypt.Encrypt;
import cn.escort.frameworkConfig.web.entity.JsonResult;
import cn.escort.web.business.base.domain.IdDto;
import cn.escort.web.business.base.domain.IdsDto;
import cn.escort.web.business.fingerprint.domain.OpenFingerprint;
import cn.escort.web.business.launcher.domain.OpenFingerprintDto;
import cn.escort.web.business.launcher.service.LauncherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Tag(name = "启动chromium接口", description = "启动chromium相关接口")
@RestController
@RequestMapping("/launcher")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LauncherController {

    private final LauncherService launcherService;

    @Operation(summary = "启动游览器",tags = {"v0.0.1"})
    @PostMapping("/open")
    public JsonResult<Boolean> open(@RequestBody @Valid IdsDto idsDto){
        return JsonResult.success(launcherService.launcherChromium(idsDto.getIds()));
    }

    @Operation(summary = "获取环境信息",tags = {"v0.0.1"})
    @PostMapping("/getOpenFingerprint")
    @Encrypt
    @Decrypt
    public JsonResult<OpenFingerprint> getOpenFingerprint(@RequestBody @Valid  OpenFingerprintDto dto){
        return JsonResult.success(launcherService.getOpenFingerprint(dto));
    }

    @Operation(summary = "关闭游览器",tags = {"v0.0.1"})
    @PostMapping("/close")
    public JsonResult<Boolean> close(@RequestBody @Valid IdDto idDto){
        return JsonResult.success(launcherService.closeChromium(idDto.getId()));
    }

    public static void main(String[] args) {
        byte[] byteArray = {-17, -68, -83, -17, -68, -77, 32, -29, -126, -76, -29, -126, -73, -29, -125, -125, -29, -126, -81};

        String str = new String(byteArray, StandardCharsets.UTF_8);
        System.out.println(str);
    }

}
