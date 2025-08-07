package cn.escort.web.business.fingerprint.controller;

import cn.escort.frameworkConfig.web.entity.JsonResult;
import cn.escort.web.business.fingerprint.domain.dto.UaByVersionDto;
import cn.escort.web.business.fingerprint.domain.dto.UserAgentDto;
import cn.escort.web.business.language.domain.vo.LanguagesVo;
import cn.escort.web.business.resolution.domain.vo.ListResolutionLVo;
import cn.escort.web.business.font.service.FontService;
import cn.escort.web.business.language.service.LangService;
import cn.escort.web.business.resolution.service.ResolutionService;
import cn.escort.web.business.timezone.domain.vo.TimezoneVo;
import cn.escort.web.business.webGlDevice.domain.vo.WebGLDeviceWithUaVo;
import cn.escort.web.business.timezone.service.TimezoneService;
import cn.escort.web.business.userAgent.service.UserAgentService;
import cn.escort.web.business.webGlDevice.service.WebGlDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "指纹接口", description = "指纹下拉的数据集合")
@RestController
@RequestMapping("/fingerprint")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FingerprintController {

    private final UserAgentService userAgentService;

    private final TimezoneService timezoneService;

    private final LangService languageService;

    private final FontService fontService;

    private final ResolutionService resolutionService;

    private final WebGlDeviceService webGlDeviceService;

    @Operation(summary = "获取Ua可设置的主版本号",tags = {"v0.0.1"})
    @PostMapping("/getUaMajorVersion")
    public JsonResult<List<Integer>> getUaMajorVersion(){
        return JsonResult.success(userAgentService.getUaMajorVersion());
    }


    @Operation(summary = "获取Ua",tags = {"v0.0.1"})
    @PostMapping("/getUaByVersion")
    public JsonResult<String> getUaByVersion(@RequestBody @Valid UaByVersionDto uaByVersionDto){
        return JsonResult.success(userAgentService.getUaByVersion(uaByVersionDto.getUaVersion()));
    }


    @Operation(summary = "获取时区列表",tags = {"v0.0.1"})
    @PostMapping("/getTimezoneList")
    public JsonResult<List<TimezoneVo>> getTimezoneList(){
        return JsonResult.success(timezoneService.getTimezoneList());
    }


    @Operation(summary = "获取语言列表",tags = {"v0.0.1"})
    @PostMapping("/getLanguagesList")
    public JsonResult<List<LanguagesVo>> getLanguagesList(){

        return JsonResult.success(languageService.getLanguagesList());
    }

    @Operation(summary = "获取字体列表",tags = {"v0.0.1"})
    @PostMapping("/getFontList")
    public JsonResult<List<String>> getFontList(){

        return JsonResult.success(fontService.getFontListAll());
    }

    @Operation(summary = "获取随机列表字体列表",tags = {"v0.0.1"})
    @PostMapping("/getRandFontList")
    public JsonResult<List<String>> getRandFontList(@RequestBody @Valid UserAgentDto userAgentDto){

        return JsonResult.success(fontService.getRandFontList(userAgentDto.getUserAgent()));
    }

    @Operation(summary = "获取分辨率列表",tags = {"v0.0.1"})
    @PostMapping("/getResolutionList")
    public JsonResult<List<ListResolutionLVo>> getResolutionList(){

        return JsonResult.success(resolutionService.getResolutionList());
    }



    @Operation(summary = "获取WebGL元数据",tags = {"v0.0.1"})
    @PostMapping("/getWebGlDevice")
    public JsonResult<WebGLDeviceWithUaVo> getWebGlDevice(@RequestBody @Valid UserAgentDto WebGLDeviceDto){

        return JsonResult.success(webGlDeviceService.getWebGlDevice(WebGLDeviceDto.getUserAgent()));
    }


}
