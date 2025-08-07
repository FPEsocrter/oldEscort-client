package cn.escort.web.business.fingerprint.domain.openFingerprint;

import lombok.Data;

@Data
public class ColoredPoint {

    private Integer row;

    private Integer column;

    private Integer red;

    private Integer green;

    private Integer blue;

    private Integer alpha;

}
