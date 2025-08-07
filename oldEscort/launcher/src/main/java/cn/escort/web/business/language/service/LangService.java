package cn.escort.web.business.language.service;

import cn.escort.web.business.language.domain.SimpleLang;
import cn.escort.web.business.language.domain.vo.LanguagesVo;

import java.util.List;

public interface LangService {

    List<LanguagesVo> getLanguagesList();

    List<SimpleLang> getLanguageByCountry(String countryCode);

}
