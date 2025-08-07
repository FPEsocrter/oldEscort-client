package cn.escort.web.business.resolution.repository;


import cn.escort.web.business.resolution.domain.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution,Long> {
}
