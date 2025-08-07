package cn.escort.web.business.timezone.repository;

import cn.escort.web.business.timezone.domain.Timezone;
import cn.escort.web.business.userAgent.domain.UaVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimezoneRepository extends JpaRepository<Timezone,Long> {


}
