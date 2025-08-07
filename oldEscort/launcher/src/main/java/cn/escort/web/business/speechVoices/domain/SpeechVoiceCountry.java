package cn.escort.web.business.speechVoices.domain;

import cn.escort.web.business.userAgent.domain.OsEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@Entity
@Table(name = "et_speech_voice_country")
@Comment("chromium语音列表")
public class SpeechVoiceCountry {

    @Id
    @Comment("主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Comment("OS")
    private OsEnum osEnum;

    @Comment("通过国家code")
    private String countryCode;

    @JdbcTypeCode( SqlTypes.JSON )
    @Comment("语言的列表")
    private List<SpeechVoice> list;

}
