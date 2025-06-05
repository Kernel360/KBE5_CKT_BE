package kernel360.ckt.admin.application;

import kernel360.ckt.admin.ui.dto.response.VehicleControlTowerResponse;
import kernel360.ckt.admin.ui.dto.response.VehicleResponse;
import kernel360.ckt.core.domain.entity.VehicleEntity;
import kernel360.ckt.core.domain.enums.VehicleControlTowerStatus;
import kernel360.ckt.core.repository.VehicleControlTowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleControlTowerService {

    private final VehicleControlTowerRepository vehicleControlTowerRepository;

    public VehicleControlTowerResponse getVehicleControlTowerStatusCount() {
        long totalCount = vehicleControlTowerRepository.countAll();
        long runningCount = vehicleControlTowerRepository.countByStatus(VehicleControlTowerStatus.RUNNING);
        long stoppedCount = vehicleControlTowerRepository.countByStatus(VehicleControlTowerStatus.STOPPED);
        long untrackedCount = vehicleControlTowerRepository.countByStatus(VehicleControlTowerStatus.UNTRACKED);

        return new VehicleControlTowerResponse(totalCount, runningCount, stoppedCount, untrackedCount);
    }

    public List<VehicleResponse> getRunningVehicles() {
        List<VehicleEntity> runningVehicles = vehicleControlTowerRepository.findByControlTowerStatus(VehicleControlTowerStatus.RUNNING);
        return runningVehicles.stream()
            .map(VehicleResponse::from)
            .toList();
    }

}
