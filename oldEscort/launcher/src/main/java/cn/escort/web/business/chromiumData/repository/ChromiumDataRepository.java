package cn.escort.web.business.chromiumData.repository;

import cn.escort.frameworkConfig.jpa.JpaBase.repository.BaseDeletedRepository;
import cn.escort.web.business.chromiumData.domain.ChromiumData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChromiumDataRepository extends BaseDeletedRepository<ChromiumData, Long> {

    @Query("from ChromiumData where environmentId = ?1")
    ChromiumData getByEnvironmentId(Long id);
}
