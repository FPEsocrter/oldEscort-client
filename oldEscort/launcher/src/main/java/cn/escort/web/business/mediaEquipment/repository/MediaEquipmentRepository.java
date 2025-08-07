package cn.escort.web.business.mediaEquipment.repository;

import cn.escort.web.business.mediaEquipment.domain.MediaEquipment;
import cn.escort.web.business.mediaEquipment.domain.MediaEquipmentEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaEquipmentRepository extends JpaRepository<MediaEquipment,Long> {

    @Query("SELECT m FROM MediaEquipment m WHERE m.type = :mediaEquipmentEnum AND m.baseCompose = 0 ORDER BY FUNCTION('RAND') LIMIT :limit")
    List<MediaEquipment> findRand(
            @Param("mediaEquipmentEnum") MediaEquipmentEnum mediaEquipmentEnum,
            @Param("limit") int limit
    );


    @Query("SELECT DISTINCT m.baseCompose FROM MediaEquipment m WHERE m.baseCompose > 0")
    List<Integer> findAllDistinctBaseComposeGreaterThanZero();


    List<MediaEquipment> findByBaseCompose(Integer baseCompose);

}
