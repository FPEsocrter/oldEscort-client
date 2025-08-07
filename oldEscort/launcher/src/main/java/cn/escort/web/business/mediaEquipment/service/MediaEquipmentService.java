package cn.escort.web.business.mediaEquipment.service;

import cn.escort.web.business.environment.domain.dto.fingerprint.MediaEquipmentDto;
import cn.escort.web.business.fingerprint.domain.openFingerprint.Equipment;

import java.util.List;


public interface MediaEquipmentService {

    List<Equipment> getMediaEquipment(MediaEquipmentDto mediaEquipment);

    List<Equipment> getMediaEquipment(MediaEquipmentDto mediaEquipment, String countryCode);

}
