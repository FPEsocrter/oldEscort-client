package cn.escort.launcher.webProxyLauncher;

import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class WebProxyParameters {

    private WebProxyEnum type;

    private String host;

    private Integer port;

    private String account;

    private String password;

    private Integer mapPort;

    private Map<String,String> other;

    public WebProxyParameters(){

    }
    public  WebProxyParameters(String host,Integer port,WebProxyEnum type){
        this.host=host;
        this.port=port;
        this.type=type;
    }
    public WebProxyParameters(String host,Integer port,String account,String password,WebProxyEnum type){
        this.host=host;
        this.port=port;
        this.account=account;
        this.password=password;
        this.type=type;

    }

}
