package cn.escort.web.business.webGlDevice.domain;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "et_webGlDevice")
public class WebGlDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long id;

    @Comment("wegGl品牌")
    @Column(nullable = false)
    private String vendors;

    @Comment("wegGl品牌型号")
    @Column(nullable = false)
    private String renderer;

    @Comment("gupGl品牌")
    @Column(nullable = false)
    private String  gpuVendors;

    @Comment("gupGl品牌2")
    @Column(nullable = false)
    private String  gpuArchitecture;

    @Comment("cpu的个数")
    private Integer coreCpu;


}
