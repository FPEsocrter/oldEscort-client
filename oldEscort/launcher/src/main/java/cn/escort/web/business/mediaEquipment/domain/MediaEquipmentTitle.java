package cn.escort.web.business.mediaEquipment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "et_media_equipment_title")
public class MediaEquipmentTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long Id;

    @Comment("国家code")
    @Column(nullable = false)
    private String countryCode;

    @Comment("默认麦克风")
    @Column(nullable = false)
    private String defaultMicrophoneLabel;

    @Comment("comm麦克风")
    @Column(nullable = false)
    private String communicationsMicrophoneLabel;


    @Comment("comm麦克风")
    @Column(nullable = false)
    private String MicrophoneLabel;


    @Comment("默认扬声器标签")
    @Column(nullable = false)
    private String defaultSpeakerLabel;

    @Comment("comm扬声器")
    @Column(nullable = false)
    private String communicationsSpeakerLabel;

    @Comment("comm扬声器")
    @Column(nullable = false)
    private String SpeakerLabel;


}
