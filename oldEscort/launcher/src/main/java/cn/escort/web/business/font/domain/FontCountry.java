package cn.escort.web.business.font.domain;

import cn.escort.web.business.userAgent.domain.OsEnum;
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
@Table(name = "et_font_country")
public class FontCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long Id;

    @Comment("国家编码")
    private String countryCode;

    @Comment("系统")
    @Enumerated(EnumType.STRING)
    private OsEnum os;

    @JdbcTypeCode( SqlTypes.JSON )
    @Comment("拥有的字体")
    private List<String> fontList;

}
