package cn.escort.web.business.font.domain;

import lombok.Data;

import java.util.List;
@Data
public class FontFamilyInfo {

    private Integer index;

    private List<String> family;

    private List<String> fullNames;

}
