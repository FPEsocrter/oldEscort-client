package cn.escort.web.business.timezone.domain.vo;

import cn.escort.frameworkConfig.web.encrypt.Encrypt;
import cn.escort.web.business.timezone.domain.Timezone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "时区信息" )
@Data
public class TimezoneVo {

    @Schema(description = "显示时区的内容 timezone+gmt 为显示内容")
    private String timezone;

    @Encrypt
    @Schema(description = "实际的内容选项")
    private String gmt;

    public static TimezoneVo Timezone(Timezone Timezone){
        TimezoneVo timezoneVo = new TimezoneVo();
        timezoneVo.setTimezone(Timezone.getTz());
        timezoneVo.setGmt(Timezone.getGmt());
        return timezoneVo;

    }

}
