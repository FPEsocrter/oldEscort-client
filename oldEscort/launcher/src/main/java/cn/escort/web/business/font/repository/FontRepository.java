package cn.escort.web.business.font.repository;

import cn.escort.web.business.font.domain.Font;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FontRepository extends JpaRepository<Font,Long> {

}
