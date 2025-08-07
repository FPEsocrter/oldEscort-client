package cn.escort.web.business.launcher.service;

import cn.escort.launcher.Launcher;
import cn.escort.web.business.fingerprint.domain.OpenFingerprint;
import cn.escort.web.business.launcher.domain.OpenFingerprintDto;

import java.util.List;
import java.util.Map;

public interface LauncherService {


    Boolean launcherChromium(List<Long> ids);
    OpenFingerprint getOpenFingerprint(OpenFingerprintDto dto);
    Boolean closeChromium(Long id);



}
