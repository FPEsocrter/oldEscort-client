package cn.escort.launcher.networkDetectionLauncher;

import lombok.Data;

import java.util.List;

@Data
public class IpDetails {

    /**
     * ip 地址
     */
  private List<String> ips;

    /**
     * ip 在的城市
     */
  private String city;
    /**
     *  ip 所在州
     */
  private String region;

    /**
     * 所在国家
     */
  private String countryCode;

    /**
     * 所在在定位 latitude
     */
  private Float latitude;

    /**
     * 所在在定位 longitude
     */
  private Float longitude;

    /**
     * 所在在定位 这个ip 要使用的 时区
     */
  private String timezone;

}
