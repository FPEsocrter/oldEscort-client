package cn.escort.web.business.siteUser.service;

import cn.escort.web.business.siteUser.repository.SiteUserRepository;
import cn.escort.web.business.siteUser.repository.SiteUserSqlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteUserServiceImpl implements SiteUserService {

    private final SiteUserRepository siteUserRepository;

    private  final SiteUserSqlRepository siteUserSqlRepository;



    @Override
    public Boolean getUserExists() {
       Long userExists = siteUserRepository.count();
       return userExists>0;
    }

}
