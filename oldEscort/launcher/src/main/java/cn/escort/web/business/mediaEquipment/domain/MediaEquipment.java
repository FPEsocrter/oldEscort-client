package cn.escort.web.business.mediaEquipment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "et_media_equipment")
public class MediaEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long Id;

    @Comment("设备名称")
    @Column(nullable = false)
    private String name;

    @Comment("设备类型")
    @Column(nullable = false)
    private MediaEquipmentEnum type;

    @Comment("设备类型组合 麦克风和扬声器基本是一组")
    @Column(nullable = false)
    private Integer baseCompose;

}
