package cn.escort.web.business.userAgent.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "et_ua_version")
public class UaVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long id;

    @Comment("主要版本号")
    private Integer uaMajorVersion;

    @Comment("完整版本号")
    private String version;



}
