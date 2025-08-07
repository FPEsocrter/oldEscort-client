package cn.escort.web.business.fingerprint.service;

import cn.escort.web.business.environment.domain.dto.FingerprintDto;
import cn.escort.web.business.fingerprint.domain.Fingerprint;
import cn.escort.web.business.fingerprint.domain.OpenFingerprint;
import cn.escort.web.business.fingerprint.domain.SimpleFingerprint;

public interface FingerprintService {

    public Fingerprint addConvert(Long envId, FingerprintDto req, String userAgent);

    public void updateConvert(FingerprintDto req, String userAgent, Fingerprint fingerprint);

    public OpenFingerprint SimpleFingerprintToOpenFingerprint(SimpleFingerprint simpleFingerprint);


}
