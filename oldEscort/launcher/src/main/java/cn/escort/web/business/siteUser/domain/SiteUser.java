package cn.escort.web.business.siteUser.domain;

import cn.escort.frameworkConfig.web.entity.DeletedEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "et_site_user")
@Comment("主键")
@EntityListeners(AuditingEntityListener.class)
public class SiteUser {

    @Id
    @Comment("主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("用户名称")
    private String username;

    @Comment("密码")
    private String password;


    @Enumerated(EnumType.ORDINAL)
    @Comment("状态")
    private DeletedEnum status;


    @CreatedDate
    @Comment("创建的时间")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Comment("最后的修改的人时间")
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Comment("创建的人")
    private String createdBy;

    @LastModifiedBy
    @Comment("最后修改的人")
    private String lastModifiedBy;

}
