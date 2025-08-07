package cn.escort.web.business.webProxy.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class IpInfo {

    @Schema(description = "ips")
    private List<String> ips;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "州/省")
    private String region;

    @Schema(description = "国家/地区")
    private String countryCode;

}
