package cn.escort.web.business.speechVoices.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
public class SpeechVoice {

    private String voiceUri;

    private String name;

    private String lang;

    private Boolean isLocalService;

    private Boolean isDefault;

}
