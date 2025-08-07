package cn.escort.web.business.speechVoices.service;

import cn.escort.web.business.speechVoices.domain.SpeechVoice;
import cn.escort.web.business.speechVoices.domain.SpeechVoiceCountry;
import cn.escort.web.business.speechVoices.repository.SpeechVoiceCountryRepository;
import cn.escort.web.business.userAgent.domain.OsEnum;
import com.sun.tools.javac.Main;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpeechVoiceCountryServiceImpl implements SpeechVoiceCountryService{

    private final SpeechVoiceCountryRepository speechVoiceCountryRepository;


    @Override
    public List<SpeechVoice> getSpeechVoiceByOsAndLang(OsEnum oSEnum, String lang) {

        SpeechVoiceCountry speechVoiceCountry = speechVoiceCountryRepository.findByOsEnumAndCountryCode(oSEnum, lang.toUpperCase());
        if(Objects.isNull(speechVoiceCountry)){
            speechVoiceCountry = speechVoiceCountryRepository.findByOsEnumAndCountryCode(OsEnum.WIN10, "US");
        }
        final SpeechVoiceCountry template = speechVoiceCountryRepository.findByOsEnumAndCountryCode(OsEnum.WIN10, "template");
        final ArrayList<SpeechVoice> list = new ArrayList<>();
        list.addAll(speechVoiceCountry.getList());
        list.addAll(template.getList());
        return list;
    }

}
