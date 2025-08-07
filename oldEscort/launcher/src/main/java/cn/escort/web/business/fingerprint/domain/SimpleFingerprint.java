package cn.escort.web.business.fingerprint.domain;


import cn.escort.web.business.environment.domain.dto.FingerprintDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class  SimpleFingerprint extends FingerprintDto implements Serializable {

    private String userAgent;

}
