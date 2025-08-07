package cn.escort.web.business.language.domain;


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
@Table(name = "et_lang_country")
public class LangCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long id;

    @Comment("国家的code")
    @Column(nullable = false)
    private String countryCode;

    @JdbcTypeCode( SqlTypes.JSON )
    @Comment("语言信息")
    private List<SimpleLang> langS;

}
