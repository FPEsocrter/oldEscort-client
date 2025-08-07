package cn.escort.web.business.language.service;

import cn.escort.web.business.language.domain.LangCountry;
import cn.escort.web.business.language.domain.SimpleLang;
import cn.escort.web.business.language.domain.vo.LanguagesVo;
import cn.escort.web.business.language.repository.LangCountryRepository;
import cn.escort.web.business.language.repository.LangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LangServiceImpl implements LangService {

    private final LangRepository languageRepository;

    private final LangCountryRepository langCountryRepository;

    @Override
    public List<LanguagesVo> getLanguagesList() {
        return languageRepository.findAll().stream().map(obj->{
            LanguagesVo languagesVo = new LanguagesVo();
            BeanUtils.copyProperties(obj,languagesVo);
            return languagesVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SimpleLang> getLanguageByCountry(String countryCode) {
        final LangCountry byCountryCode = langCountryRepository.findByCountryCode(countryCode.toUpperCase());
        if(Objects.isNull(byCountryCode)){
             return langCountryRepository.findByCountryCode("US").getLangS();
        }
        return byCountryCode.getLangS();
    }
}
