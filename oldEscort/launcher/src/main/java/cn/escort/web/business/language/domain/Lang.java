package cn.escort.web.business.language.domain;


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
@Table(name = "et_lang")
public class Lang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long id;

    @Comment("语言的code, 实际使用的值")
    @Column(nullable = false)
    private String code;

    @Comment("语言的中文名称")
    @Column(nullable = false)
    private String cnName;

    @Comment("语言的英文名称")
    @Column(nullable = false)
    private String enName;

    @Comment("语言自身的语言显示")
    @Column(nullable = false)
    private String nativeName;

    @Comment("是否可以作为置顶(置顶为界面语言)")
    @Column(nullable = false)
    private Boolean supportsUI;

}
