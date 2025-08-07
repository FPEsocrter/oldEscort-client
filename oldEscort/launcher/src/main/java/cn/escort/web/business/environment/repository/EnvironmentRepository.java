package cn.escort.web.business.environment.repository;

import cn.escort.frameworkConfig.jpa.JpaBase.repository.BaseDeletedRepository;
import cn.escort.web.business.environment.domain.Environment;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends BaseDeletedRepository<Environment, Long> {


}
