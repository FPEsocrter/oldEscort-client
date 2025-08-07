package cn.escort.web.business.language.repository;

import cn.escort.web.business.language.domain.Lang;
import cn.escort.web.business.language.domain.LangCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LangRepository extends JpaRepository<Lang,Long> {
}
