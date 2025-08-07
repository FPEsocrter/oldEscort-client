package cn.escort.web.business.font.repository;

import cn.escort.web.business.font.domain.FontCountry;
import cn.escort.web.business.userAgent.domain.OsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FontCountryRepository extends JpaRepository<FontCountry,Long> {

    FontCountry findByOsAndCountryCode(OsEnum os, String countryCode);


}
