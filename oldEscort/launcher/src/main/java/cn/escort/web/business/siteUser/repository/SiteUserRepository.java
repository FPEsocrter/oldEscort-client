package cn.escort.web.business.siteUser.repository;

import cn.escort.web.business.siteUser.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {

    @Query(value = "select count(*) from et_site_user",nativeQuery = true)
    public void test();
}
