package cn.escort.frameworkConfig.jpa.JpaBase.domain;

import cn.escort.frameworkConfig.web.entity.DeletedEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import org.hibernate.annotations.Comment;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class BaseDeletedEntry extends BaseModifyEntry {


    @Enumerated(EnumType.ORDINAL)
    @Comment("状态 用于逻辑删除")
    protected DeletedEnum deleted;

}
