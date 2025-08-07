package cn.escort.launcher.chromiumLauncher;

import cn.escort.launcher.webProxyLauncher.InetAddress;
import cn.escort.web.business.chromiumData.domain.ChromiumData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Chromium {

    private String aipKey;

    private InetAddress webProxyInetAddress;

    private ChromiumData chromiumData;

    private String showUserAgent;

    private String lang;//界面语言

    private List<String> chromeCommand;

    public Chromium(String aipKey, InetAddress webProxyInetAddress, ChromiumData chromiumData, String showUserAgent, String lang) {
        this.aipKey = aipKey;
        this.webProxyInetAddress = webProxyInetAddress;
        this.chromiumData = chromiumData;
        this.showUserAgent = showUserAgent;
        this.lang = lang;
    }
}
