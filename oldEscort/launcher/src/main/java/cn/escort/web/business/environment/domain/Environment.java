package cn.escort.web.business.environment.domain;

import cn.escort.frameworkConfig.jpa.JpaBase.domain.BaseDeletedEntry;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;


@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "et_environment")
@EntityListeners(AuditingEntityListener.class)
public class Environment extends BaseDeletedEntry {

    @Comment("唯一序号")
    private Long serialNumber;

    @Comment("环境名称")
    private String name;

    @Comment("分组")
    private Long groupId;

    @Comment("分组名称")
    private String groupName;

    @Comment("备注")
    private String remark;

    @Comment("最后使用的ip")
    private String lastUseIp;

    @Comment("地区")
    private String area;

    @Comment("最后打开的时间")
    private LocalDateTime lastOpenTime;

    @Enumerated(EnumType.ORDINAL)
    @Comment("代理类型")
    private WebProxyEnum WebProxy;

    @Comment("代理的id")
    private Long useProxyId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Comment("代理的信息")
    private Map<String,String> ProxyInfo;

    @Comment("ua版本")
    private String UaVersion;

    @Comment("核心版本 这里内核的版本")
    private String coreVersion;



}
