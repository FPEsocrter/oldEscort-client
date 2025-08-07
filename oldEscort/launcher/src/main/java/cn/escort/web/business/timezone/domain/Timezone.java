package cn.escort.web.business.timezone.domain;

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
@Table(name = "et_timezone")
public class Timezone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long Id;

    @Comment("时区显示")
    @Column(nullable = false)
    private String tz;

    @Comment("时区使用的值")
    @Column(nullable = false)
    private String gmt;


}
