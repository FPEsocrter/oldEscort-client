package cn.escort.web.business.mediaEquipment.service;

import cn.escort.web.business.environment.domain.dto.fingerprint.MediaEquipmentDto;
import cn.escort.web.business.fingerprint.domain.openFingerprint.Equipment;
import cn.escort.web.business.mediaEquipment.domain.MediaEquipment;
import cn.escort.web.business.mediaEquipment.domain.MediaEquipmentEnum;
import cn.escort.web.business.mediaEquipment.domain.MediaEquipmentTitle;
import cn.escort.web.business.mediaEquipment.repository.MediaEquipmentRepository;
import cn.escort.web.business.mediaEquipment.repository.MediaEquipmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class



MediaEquipmentServiceImpl implements MediaEquipmentService{

    private final MediaEquipmentTypeRepository mediaEquipmentTypeRepository;

    private final MediaEquipmentRepository mediaEquipmentRepository;




    @Override
    public List<Equipment> getMediaEquipment(MediaEquipmentDto dto){
            return  getMediaEquipment(dto,"us");
    }

    @Override
    public List<Equipment> getMediaEquipment(MediaEquipmentDto dto, String countryCode) {

        MediaEquipmentTitle byCountryCode = mediaEquipmentTypeRepository.findByCountryCode(countryCode);
        if(Objects.isNull(byCountryCode)){
            byCountryCode = mediaEquipmentTypeRepository.findByCountryCode("us");
        }

        List<Integer> allDistinctBaseCompose = mediaEquipmentRepository.findAllDistinctBaseComposeGreaterThanZero();
        int i = new Random().nextInt(allDistinctBaseCompose.size());
        List<MediaEquipment> mediaEquipmentList =mediaEquipmentRepository.findByBaseCompose(allDistinctBaseCompose.get(i));

        Map<MediaEquipmentEnum, List<Equipment>> mediaEquipmentEnumListMap = baseCompose(byCountryCode, mediaEquipmentList);

        ArrayList<Equipment> list = new ArrayList<>(mediaEquipmentEnumListMap.get(MediaEquipmentEnum.Microphone));

        if(dto.getMicrophone()-1>0){
            List<MediaEquipment> microphoneList =mediaEquipmentRepository.findRand(MediaEquipmentEnum.Microphone,dto.getMicrophone()-1);
            microphoneList.forEach(mediaEquipment->{
                Equipment defaultMicrophone = new Equipment();
                defaultMicrophone.setType(MediaEquipmentEnum.Microphone);
                defaultMicrophone.setDeviceId("audioinput");
                defaultMicrophone.setLabel(mediaEquipment.getName());
                defaultMicrophone.setDeviceId("default");
                defaultMicrophone.setGroupId(generateRandomString());
                list.add(defaultMicrophone);
            });
        }
        if(dto.getVideoCamera()>0){
            List<MediaEquipment> videoCamera =mediaEquipmentRepository.findRand(MediaEquipmentEnum.Camera,dto.getVideoCamera()-1);
            videoCamera.forEach(mediaEquipment->{
                Equipment defaultMicrophone = new Equipment();
                defaultMicrophone.setType(MediaEquipmentEnum.Camera);
                defaultMicrophone.setDeviceId("videoinput");
                defaultMicrophone.setLabel(mediaEquipment.getName());
                defaultMicrophone.setDeviceId("default");
                defaultMicrophone.setGroupId(generateRandomString());
                list.add(defaultMicrophone);
            });
        }
        list.addAll(mediaEquipmentEnumListMap.get(MediaEquipmentEnum.Speaker));
        if(dto.getSpeaker()-1>0){
            List<MediaEquipment> speakerList =mediaEquipmentRepository.findRand(MediaEquipmentEnum.Speaker,dto.getSpeaker()-1);
            speakerList.forEach(mediaEquipment->{
                Equipment defaultMicrophone = new Equipment();
                defaultMicrophone.setType(MediaEquipmentEnum.Speaker);
                defaultMicrophone.setDeviceId("audiooutput");
                defaultMicrophone.setLabel(mediaEquipment.getName());
                defaultMicrophone.setDeviceId("default");
                defaultMicrophone.setGroupId(generateRandomString());
                list.add(defaultMicrophone);
            });
        }

        return list;
    }

    private Map<MediaEquipmentEnum,List<Equipment>> baseCompose(MediaEquipmentTitle byCountryCode,List<MediaEquipment> mediaEquipmentList){
        String groupId = generateRandomString();
        Map<MediaEquipmentEnum,List<Equipment>> map = new HashMap<>();
        Map<MediaEquipmentEnum, String> collect = mediaEquipmentList.stream().collect(Collectors.toMap(MediaEquipment::getType, MediaEquipment::getName));
        String microphoneName = collect.get(MediaEquipmentEnum.Microphone);
        ArrayList<Equipment> microphoneList = new ArrayList<>();
        defaultMicrophone:{
            Equipment defaultMicrophone = new Equipment();
            defaultMicrophone.setType(MediaEquipmentEnum.Microphone);
            defaultMicrophone.setDeviceId("audioinput");
            defaultMicrophone.setLabel(Label(byCountryCode.getDefaultMicrophoneLabel())+microphoneName);
            defaultMicrophone.setDeviceId("default");
            defaultMicrophone.setGroupId(groupId);
            microphoneList.add(defaultMicrophone);
        }
        communicationsMicrophone:{
            Equipment defaultMicrophone = new Equipment();
            defaultMicrophone.setDeviceId("audioinput");
            defaultMicrophone.setType(MediaEquipmentEnum.Microphone);
            defaultMicrophone.setLabel(Label(byCountryCode.getCommunicationsMicrophoneLabel())+microphoneName);
            defaultMicrophone.setDeviceId("communications");
            defaultMicrophone.setGroupId(groupId);
            microphoneList.add(defaultMicrophone);
        }

        Microphone:{
            Equipment defaultMicrophone = new Equipment();
            defaultMicrophone.setType(MediaEquipmentEnum.Microphone);
            defaultMicrophone.setDeviceId("audioinput");
            defaultMicrophone.setLabel(Label(byCountryCode.getMicrophoneLabel())+microphoneName);
            defaultMicrophone.setDeviceId(generateRandomString());
            defaultMicrophone.setGroupId(groupId);
            microphoneList.add(defaultMicrophone);
        }

        map.put(MediaEquipmentEnum.Microphone,microphoneList);

        ArrayList<Equipment> SpeakerList = new ArrayList<>();
        String speaker = collect.get(MediaEquipmentEnum.Speaker);


        defaultSpeaker:{
            Equipment defaultMicrophone = new Equipment();
            defaultMicrophone.setType(MediaEquipmentEnum.Speaker);
            defaultMicrophone.setDeviceId("audioinput");
            defaultMicrophone.setLabel(Label(byCountryCode.getDefaultSpeakerLabel())+speaker);
            defaultMicrophone.setDeviceId("default");
            defaultMicrophone.setGroupId(groupId);
            SpeakerList.add(defaultMicrophone);

        }

        CommunicationsSpeaker:{
            Equipment defaultMicrophone = new Equipment();
            defaultMicrophone.setType(MediaEquipmentEnum.Speaker);
            defaultMicrophone.setDeviceId("audioinput");
            defaultMicrophone.setLabel(Label(byCountryCode.getCommunicationsSpeakerLabel())+speaker);
            defaultMicrophone.setDeviceId("communications");
            defaultMicrophone.setGroupId(groupId);
            SpeakerList.add(defaultMicrophone);
        }

        Speaker:{
            Equipment defaultMicrophone = new Equipment();
            defaultMicrophone.setType(MediaEquipmentEnum.Speaker);
            defaultMicrophone.setDeviceId("audioinput");
            defaultMicrophone.setLabel(Label(byCountryCode.getSpeakerLabel())+speaker);
            defaultMicrophone.setDeviceId(generateRandomString());
            defaultMicrophone.setGroupId(groupId);
            SpeakerList.add(defaultMicrophone);
        }

        map.put(MediaEquipmentEnum.Speaker,SpeakerList);

        return map;

    }

    private String Label(String prefixLabel){
        if(prefixLabel.indexOf("(")==0){
            return prefixLabel;
        }

        String[] parts = prefixLabel.split("-");
        if(parts.length==1){
            return prefixLabel;
        }
        parts[parts.length-1]="";

        return String.join("-", parts);

    }

    private  String generateRandomString() {
        String characters = "0123456789abcdef"; // 可以包含的字符集合
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(64);

        for (int i = 0; i < 64; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }




}
