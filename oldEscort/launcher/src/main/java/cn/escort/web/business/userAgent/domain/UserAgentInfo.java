package cn.escort.web.business.userAgent.domain;

import cn.escort.web.business.font.domain.INOSEnum;
import lombok.Data;

@Data
public class UserAgentInfo {


    private Boolean mobile;

    private PlatformEnum platform;

    private PlatformVersionEnum  platformVersion;

    private ArchitectureEnum architecture;

    private BitnessEnum bitness;

    private Boolean wow64;

    private String model;

    private String uaFullVersion;


    private OsEnum osVersion;

    private INOSEnum os;

    /**
     * Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36
     *  10.0
     */
    private String osArchitecture;

    /**
     * 117.0.5938.89
     */
    private String chromeVersion;

    /**
     * chrome 的核心版本   比如117.0.5938.89   核心版本 117.0.0.0
     */
    public String showVersion;

    /**
     * 比如
     * Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.5938.89  Safari/537.36
     * Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36
     */
    public String showUserAgent;



    /**
     * chrome 的核心版本   比如117.0.5938.89   核心版本 177
     */
    private Integer uaMajorVersion;


}
