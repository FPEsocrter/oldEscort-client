package cn.escort.web.business.speechVoices.service;

import cn.escort.web.business.speechVoices.domain.SpeechVoice;
import cn.escort.web.business.userAgent.domain.OsEnum;

import java.util.List;

public interface SpeechVoiceCountryService {


    List<SpeechVoice> getSpeechVoiceByOsAndLang(OsEnum oSEnum, String lang);


}
