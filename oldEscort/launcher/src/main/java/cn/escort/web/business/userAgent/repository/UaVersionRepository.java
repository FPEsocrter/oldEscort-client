package cn.escort.web.business.userAgent.repository;

import cn.escort.web.business.userAgent.domain.UaVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UaVersionRepository extends JpaRepository<UaVersion,Long> {

    @Query(value = "select distinct ua_major_version from et_ua_version ",nativeQuery = true)
    List<Integer> getUaMajorVersion();

    List<UaVersion> findByUaMajorVersionIn(List<Integer> uaVersion);


    UaVersion findFirstByOrderByIdDesc();
}
