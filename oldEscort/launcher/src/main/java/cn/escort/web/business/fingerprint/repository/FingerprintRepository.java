package cn.escort.web.business.fingerprint.repository;

import cn.escort.frameworkConfig.jpa.JpaBase.repository.BaseDeletedRepository;
import cn.escort.web.business.fingerprint.domain.Fingerprint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FingerprintRepository extends BaseDeletedRepository<Fingerprint, Long> {

    @Query("from Fingerprint where environmentId = ?1")
    Fingerprint getByEnvironmentId(Long id);
}
