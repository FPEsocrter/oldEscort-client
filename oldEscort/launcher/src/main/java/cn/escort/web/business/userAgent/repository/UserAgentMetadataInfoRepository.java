package cn.escort.web.business.userAgent.repository;

import cn.escort.web.business.userAgent.domain.UaVersion;
import cn.escort.web.business.userAgent.domain.UserAgentMetadataInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAgentMetadataInfoRepository extends JpaRepository<UserAgentMetadataInfo,Long> {

    UserAgentMetadataInfo findByUaMajorVersion(Integer uaVersion);

}
