package cn.escort.frameworkConfig.web.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "分页数据")
@Data
public class Page<T> extends SelectPage{

    @Schema(description = "数据集合")
    private List<T> list;

    @Schema(description = "数据的总数")
    private long total;

    public Page(SelectPage selectPage){
        this.setCurrentPage(selectPage.getCurrentPage());
        this.setPageSize(selectPage.getPageSize());
    }


}
