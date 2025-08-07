package cn.escort.web.business.mediaEquipment.repository;

import cn.escort.web.business.mediaEquipment.domain.MediaEquipmentTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaEquipmentTypeRepository extends JpaRepository<MediaEquipmentTitle,Long> {

    MediaEquipmentTitle findByCountryCode(String countryCode);

}
