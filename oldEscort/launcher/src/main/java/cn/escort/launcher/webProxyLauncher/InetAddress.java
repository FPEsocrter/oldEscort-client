package cn.escort.launcher.webProxyLauncher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InetAddress {

    private String address;

    private Integer port;

    public int webProxyType;

}
