package cn.escort.frameworkConfig.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class SelectPage {

    @Schema(description = "页码")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须大于或等于1")
    private int currentPage = 1;

    @Schema(description = "分页的大小")
    @NotNull(message = "分页的大小不能为空")
    @Range(min = 10,max = 50, message = "有效分页的大小为10到50")
    private int pageSize = 10;

    @JsonIgnore
    public SelectPage getPage(){
        return this;
    }

}
