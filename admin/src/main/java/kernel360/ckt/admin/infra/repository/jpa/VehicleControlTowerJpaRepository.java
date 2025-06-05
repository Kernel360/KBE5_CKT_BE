package kernel360.ckt.admin.infra.repository.jpa;

import kernel360.ckt.core.domain.entity.VehicleEntity;
import kernel360.ckt.core.repository.VehicleControlTowerRepository;
import org.springframework.data.repository.Repository;

public interface VehicleControlTowerJpaRepository extends Repository<VehicleEntity, Long>, VehicleControlTowerRepository {

}
