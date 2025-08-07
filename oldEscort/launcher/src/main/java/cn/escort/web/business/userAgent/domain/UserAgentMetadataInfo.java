package cn.escort.web.business.userAgent.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "et_userAgent_metadata_info")
public class UserAgentMetadataInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long id;

    @Comment("主要版本号")
    private Integer uaMajorVersion;

    @JdbcTypeCode( SqlTypes.JSON )
    @Comment("brands")
    private List<BrandVersion> brands;

    @JdbcTypeCode( SqlTypes.JSON )
    @Comment("fullVersionList")
    private List<BrandVersion> fullVersionList;
    
}
