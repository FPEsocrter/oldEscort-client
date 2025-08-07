package cn.escort.web.business.font.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "et_font")
public class Font {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long Id;

    @Comment("字体的名称")
    private String name;

    @JdbcTypeCode( SqlTypes.JSON )
    @Enumerated(EnumType.STRING)
    @Comment("在什么系统下可以用的字体,比如在win7下必要的字体,有些不能在win10下有")
    private List<INOSEnum> inOs;

}
