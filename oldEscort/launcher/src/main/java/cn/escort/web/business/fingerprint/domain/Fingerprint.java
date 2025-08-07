package cn.escort.web.business.fingerprint.domain;

import cn.escort.frameworkConfig.jpa.JpaBase.domain.BaseDeletedEntry;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "et_fingerprint")
@EntityListeners(AuditingEntityListener.class)
public class Fingerprint extends BaseDeletedEntry implements Serializable {


    @Comment("环境id")
    @Column(unique = true)
    private Long environmentId;


    @JdbcTypeCode(SqlTypes.JSON )
    @Comment("指纹入库时候的信息")
    private SimpleFingerprint addFingerprint;


    @JdbcTypeCode( SqlTypes.JSON )
    @Comment("环境打开需要的指纹信息")
    private OpenFingerprint openFingerprint;


    @JdbcTypeCode( SqlTypes.JSON )
    @Comment("环境静态代理打开需要的指纹信息")
    private OpenFingerprint stableOpenFingerprint;//最后一个开打用的指纹信息,用于静态ip


}
