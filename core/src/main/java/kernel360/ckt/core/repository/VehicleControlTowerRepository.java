package kernel360.ckt.core.repository;

import kernel360.ckt.core.domain.entity.VehicleEntity;
import kernel360.ckt.core.domain.enums.VehicleControlTowerStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleControlTowerRepository {

    @Query("SELECT COUNT(v) FROM VehicleEntity v")
    long countAll();

    @Query("SELECT COUNT(v) FROM VehicleEntity v WHERE v.ControlTowerstatus = :status")
    long countByStatus(@Param("status") VehicleControlTowerStatus status);

    @Query("SELECT v FROM VehicleEntity v WHERE v.ControlTowerstatus = :status")
    List<VehicleEntity> findByControlTowerStatus(@Param("status") VehicleControlTowerStatus status);

}
