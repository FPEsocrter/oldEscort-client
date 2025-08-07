package cn.escort.web.business.chromiumData.domain;

import cn.escort.frameworkConfig.jpa.JpaBase.domain.BaseDeletedEntry;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "et_chromium_data")
@EntityListeners(AuditingEntityListener.class)
public class ChromiumData extends BaseDeletedEntry {

    @Comment("环境id")
    @Column(unique = true)
    private Long environmentId;

    @Comment("环境数据给唯一名称")
    private String path;

    @Comment("cookie存储的位置")
    private String cookiePath;


}
