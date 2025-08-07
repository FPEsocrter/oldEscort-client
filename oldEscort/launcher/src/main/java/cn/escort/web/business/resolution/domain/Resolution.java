package cn.escort.web.business.resolution.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "et_resolution")
public class Resolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long id;

    @Comment("屏幕宽")
    private Integer width;

    @Comment("屏幕高")
    private Integer height;

    //work 高是减40

}
