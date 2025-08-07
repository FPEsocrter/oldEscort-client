package cn.escort.web.business.webGlDevice.repository;

import cn.escort.web.business.webGlDevice.domain.WebGlDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebGlDeviceRepository extends JpaRepository<WebGlDevice,Long> {

    WebGlDevice findTopByOrderByIdDesc();

    WebGlDevice findTopByIdLessThanOrderByIdDesc( Long id);

    WebGlDevice findByRenderer(String renderer);

    WebGlDevice findTopByVendors(String vendors);
}
