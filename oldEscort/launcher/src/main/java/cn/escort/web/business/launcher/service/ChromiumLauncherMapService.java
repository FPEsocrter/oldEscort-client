package cn.escort.web.business.launcher.service;

import cn.escort.launcher.Launcher;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChromiumLauncherMapService {
    public Map<Long, Launcher> chromiumLauncherMap=new ConcurrentHashMap<>();

    public Map<Long, Launcher> getChromiumLauncherMap() {
        return chromiumLauncherMap;
    }
}
