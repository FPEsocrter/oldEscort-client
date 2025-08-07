package cn.escort.web.business.environment.domain;

import cn.escort.frameworkConfig.jpa.JpaBase.domain.BaseDeletedEntry;
import cn.escort.web.business.fingerprint.domain.OpenFingerprint;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "et_fingerprint")
@EntityListeners(AuditingEntityListener.class)
public class EnvironmentConfiguration extends BaseDeletedEntry {


    @JdbcTypeCode( SqlTypes.JSON )
    @Comment("指纹入库时候的信息")
    private String addFingerprint;


    @JdbcTypeCode( SqlTypes.JSON )
    @Comment("环境打开需要的指纹信息")
    private OpenFingerprint openFingerprint;



}
