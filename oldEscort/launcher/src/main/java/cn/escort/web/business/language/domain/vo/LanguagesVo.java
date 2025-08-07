package cn.escort.web.business.language.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.Comment;

@Schema(description = "语言选项")
@Data
public class LanguagesVo {

    @Schema(description = "语言的code 实际需要上传的值")
    private String code;

    @Schema(description = "显示中文名称 显示名称= cnName_enName")
    private String cnName;

    @Schema(description = "显示英文名称")
    private String enName;

    @Comment("语言的英文名称")
    private String nativeName;

    @Comment("是否可以作为置顶(置顶为界面语言)")
    private Boolean supportsUI;

}
