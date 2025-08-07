package cn.escort.frameworkConfig.jpa.JpaBase.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@MappedSuperclass
public abstract  class BaseModifyEntry implements Serializable {

    //自增主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    protected Long Id;

    @CreatedDate
    @Comment("创建的时间")
    protected LocalDateTime createdDate;

    @LastModifiedDate
    @Comment("最后的修改的人时间")
    protected LocalDateTime lastModifiedDate;

    @CreatedBy
    @Comment("创建的人")
    protected String createdBy;

    @LastModifiedBy
    @Comment("最后修改的人")
    protected String lastModifiedBy;


}
