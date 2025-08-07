package cn.escort.web.business.fingerprint.domain.openFingerprint;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FontInfo {

    /**
     *序号   在系统字体的后面的序号 -1 为系统字体
     */
    private Long id;

    /**
     * 0.7 到 1.7 随机倍数
     */
    private Double width;

    /**
     * 0.7 到 1.7 随机倍数
     */
    private Double height;

    /**
     *  随机 0 到10
     */
    private Double actualBoundingBoxAscent;

    /**
     *  随机 0 到10
     */
    private Double actualBoundingBoxDescent;

    /**
     *  随机 0 到10
     */
    private Double actualBoundingBoxLeft;

    /**
     *  随机 0 到10
     */
    private Double actualBoundingBoxRight;

    /**
     *  随机 0 到10
     */
    private Double fontBoundingBoxAscent;

    /**
     *  随机 0 到10
     */
    private Double fontBoundingBoxDescent;

    //自定字体加载 暂时为实现
    private List<String> filePaths=new ArrayList<>();

}
