package cn.escort.web.business.webGlDevice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WebGlDevice接口", description = "WebGlDevice相关接口")
@RestController
@RequestMapping("/webGlDevice")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebGlDeviceController {

}
