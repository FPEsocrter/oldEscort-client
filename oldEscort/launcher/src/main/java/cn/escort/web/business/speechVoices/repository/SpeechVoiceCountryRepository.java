package cn.escort.web.business.speechVoices.repository;

import cn.escort.web.business.speechVoices.domain.SpeechVoiceCountry;
import cn.escort.web.business.userAgent.domain.OsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeechVoiceCountryRepository extends JpaRepository<SpeechVoiceCountry, Long> {

    SpeechVoiceCountry findByOsEnumAndCountryCode(OsEnum osEnum, String countryCode);

}
