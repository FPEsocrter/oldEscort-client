package cn.escort.web.business.language.domain;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
public class SimpleLang {

    @Comment("语言的code, 实际使用的值")
    @Column(nullable = false)
    private String code;

    @Comment("是否可以作为置顶(置顶为界面语言)")
    @Column(nullable = false)
    private Boolean supportsUI;

}
