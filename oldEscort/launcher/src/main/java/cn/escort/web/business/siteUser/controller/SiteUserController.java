package cn.escort.web.business.siteUser.controller;

import cn.escort.frameworkConfig.web.entity.JsonResult;
import cn.escort.web.business.siteUser.service.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/siteUser")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteUserController {

    private final SiteUserService siteUserService;

    @GetMapping("/getUserExists")
    public JsonResult<Boolean> getUserExists() {
        return JsonResult.success(siteUserService.getUserExists());
    }


}
