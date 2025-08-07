package cn.escort.web.business.fingerprint.domain.openFingerprint;

import cn.escort.web.business.fingerprint.domain.typeEnum.OpenCustomizeTypeEnum;
import cn.escort.web.business.userAgent.domain.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class OpenUserAgentMetadata {

    private OpenCustomizeTypeEnum type;

    private List<BrandVersion> brandVersionList;

    private Boolean mobile;

    @Enumerated(EnumType.STRING)
    private PlatformEnum platform;

    @Enumerated(EnumType.STRING)
    private PlatformVersionEnum platformVersion;

    @Enumerated(EnumType.STRING)
    private ArchitectureEnum architecture;

    @Enumerated(EnumType.STRING)
    private BitnessEnum bitness;

    private Boolean wow64;

    private String model;

    private String fullVersion;

    private List<BrandVersion> brandFullVersionList;

}
