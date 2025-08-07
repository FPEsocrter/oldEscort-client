package cn.escort.web.business.environment.controller;

import cn.escort.frameworkConfig.web.entity.JsonResult;
import cn.escort.frameworkConfig.web.entity.Page;
import cn.escort.frameworkConfig.web.exceptionHandler.exception.ExceptionUtil;
import cn.escort.web.business.base.domain.IdsDto;
import cn.escort.web.business.environment.domain.dto.*;
import cn.escort.web.business.environment.domain.dto.fingerprint.*;
import cn.escort.web.business.environment.domain.vo.EnvironmentByIdVo;
import cn.escort.web.business.environment.domain.vo.EnvironmentPageVo;
import cn.escort.web.business.environment.service.EnvironmentService;
import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeOnTypeEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.CustomizeTypeEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.FollowIpTypeEnum;
import cn.escort.web.business.fingerprint.domain.typeEnum.LocationEnum;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "环境接口", description = "环境相关CRUD接口")
@RestController
@RequestMapping("/environment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @Operation(summary = "添加环境",tags = {"v0.0.1"})
    @PostMapping("/add")
    public JsonResult<Boolean> add(@RequestBody @Valid AddEnvironmentDto addEnvironmentReq) {

        validWebProxy(addEnvironmentReq.getWebProxy());

        validFingerprint(addEnvironmentReq.getFingerprint());

        return JsonResult.success(environmentService.add(addEnvironmentReq));
    }

    @Operation(summary = "删除环境",tags = {"v0.0.1"})
    @PostMapping("/delete")
    public JsonResult<Boolean> delete(@RequestBody @Valid IdsDto idDto) {

        return JsonResult.success(environmentService.delete(idDto.getIds()));
    }

    @Operation(summary = "修改环境",tags = {"v0.0.1"})
    @PostMapping("/modify")
    public JsonResult<Boolean> modify(@RequestBody @Valid ModifyEnvironmentDto modifyEnvironmentReq) {


        if(!Objects.isNull(modifyEnvironmentReq.getWebProxy())){
            validWebProxy(modifyEnvironmentReq.getWebProxy());
        }
        if(!Objects.isNull(modifyEnvironmentReq.getFingerprint())){
            validFingerprint(modifyEnvironmentReq.getFingerprint());
        }

        return JsonResult.success(environmentService.modify(modifyEnvironmentReq));
    }


    @Operation(summary = "获取环境列表",tags = {"v0.0.1"})
    @PostMapping("/page")
    public JsonResult<Page<EnvironmentPageVo>> page(@RequestBody ListEnvironmentDto listEnvironmentReq) {

        return JsonResult.success(environmentService.page(listEnvironmentReq));
    }

    @Operation(summary = "获取单个环境数据",tags = {"v0.0.1"})
    @PostMapping("/get")
    public JsonResult<EnvironmentByIdVo> getById(@RequestBody @Valid ByIdEnvironmentDto byIdEnvironmentReq) {
        return JsonResult.success(environmentService.getById(byIdEnvironmentReq));
    }


    private void validWebProxy(WebProxyDto webProxy){
        if(!webProxy.getType().equals(WebProxyEnum.NO_PROXY)){
            ExceptionUtil.requireNonNull(webProxy.getHost(),"主机不能为空");
            ExceptionUtil.requireNonNumberRange(webProxy.getPort(),0,65535,"端口不能空","端口号序号在0到65535内");
        }
        if(Objects.nonNull(webProxy.getOther())&& !webProxy.getOther().isEmpty()){
            List<String> keys = webProxy.getOther().keySet().stream().map(String::toLowerCase).collect(Collectors.toList());
            ExceptionUtil.requireNoNExist(keys,Arrays.asList("type","host","port","account","password"),"这些字段已被使用无法(type,host,port,account,password)");
        }
    }


    private  void validFingerprint(FingerprintDto fingerprint){
        if(FollowIpTypeEnum.CUSTOMIZE.equals(fingerprint.getTimeZone().getType())){
            ExceptionUtil.requireNonNull(fingerprint.getTimeZone());
        }

        LocationDto location = fingerprint.getLocation();
        if(LocationEnum.ALLOW_CUSTOMIZE.equals(location.getType())||
           LocationEnum.CALL_CUSTOMIZE.equals(location.getType())){

            ExceptionUtil.requireNonNumberRange(location.getLatitude(),-90f,90f,"纬度不能空","纬度的范围在-90到90内");
            ExceptionUtil.requireNonNumberRange(location.getLongitude(),-180f,180f,"经度不能空","经度的范围在-180到180");
            ExceptionUtil.requireNonNumberRange(location.getAccuracy(),10,1000,"范围不能空","范围在10到1000内");

        }

        ResolutionDto resolution = fingerprint.getResolution();
        if(CustomizeTypeEnum.CUSTOMIZE.equals(resolution.getType())){
            ExceptionUtil.requireNonNumberRange(resolution.getWindowWidth(),0,6000,"分辨率的宽不能空","分辨率的宽0到6000");
            ExceptionUtil.requireNonNumberRange(resolution.getWindowHeight(),0,3000,"分辨率的高不能空","分辨率的高0到3000");
        }

        FontDto font = fingerprint.getFont();
        if(CustomizeOnTypeEnum.CUSTOMIZE.equals(font.getType())){
            ExceptionUtil.requireNonNull(font.getFontList(),"字体列表不能空");
        }


        WebGLDeviceDto webGLDevice = fingerprint.getWebGLDevice();
        if(CustomizeOnTypeEnum.CUSTOMIZE.equals(webGLDevice.getType())){
            ExceptionUtil.requireNonNull(webGLDevice.getVendors(),"显卡品牌不能空");
            ExceptionUtil.requireNonNull(webGLDevice.getRenderer(),"显卡信息不能空");
        }

        MediaEquipmentDto mediaEquipment = fingerprint.getMediaEquipment();
        if(CustomizeOnTypeEnum.CUSTOMIZE.equals(mediaEquipment.getType())){
            ExceptionUtil.requireNonNumberRange(mediaEquipment.getMicrophone(),0,10,"麦克风的个数1到10");
            ExceptionUtil.requireNonNumberRange(mediaEquipment.getSpeaker(),0,10,"扬声器不能为空","扬声器的个数1到10");
        }

        ResourceInfoDto resourceInfo = fingerprint.getResourceInfo();
        if(CustomizeTypeEnum.CUSTOMIZE.equals(resolution.getType())){
            ExceptionUtil.requireNoNExist(resourceInfo.getCpu(), Arrays.asList(2,4,6,8,10,12,16,18,20,24,32),"cpu个数不能为空","cpu的有效的个为2,4,6,8,10,12,16,18,20,24,32");
            ExceptionUtil.requireNoNExist(resourceInfo.getMemory(),Arrays.asList(2,4,6,8),"内存值不能空","内存的有效值为2,4,6,8");
        }

        OpenPortDto openPort = fingerprint.getOpenPort();
        if(CustomizeTypeEnum.CUSTOMIZE.equals(openPort.getType())){
            ExceptionUtil.requireNonNull(openPort.getList(),"开发端口不能为空");
        }

    }



}
