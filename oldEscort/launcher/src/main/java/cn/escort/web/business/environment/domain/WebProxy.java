package cn.escort.web.business.environment.domain;

import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class WebProxy {

    private WebProxyEnum type;

    private String host;

    private Integer port;

    private String account;

    private String password;

    private Map<String,String> other;

}
