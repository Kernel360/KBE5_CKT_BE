package kernel360.ckt.admin.ui;

import kernel360.ckt.admin.application.VehicleControlTowerService;
import kernel360.ckt.admin.ui.dto.response.VehicleControlTowerResponse;
import kernel360.ckt.admin.ui.dto.response.VehicleResponse;
import kernel360.ckt.core.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tracking")
public class VehicleControlTowerController {

    private final VehicleControlTowerService vehicleControlTowerService;

    @GetMapping("/status")
    public CommonResponse<VehicleControlTowerResponse> getVehicleStatusCount() {
        VehicleControlTowerResponse response = vehicleControlTowerService.getVehicleControlTowerStatusCount();
        return CommonResponse.success(response);
    }

    @GetMapping("/vehicles/running")
    public CommonResponse<List<VehicleResponse>> getRunningVehicles() {
        List<VehicleResponse> response = vehicleControlTowerService.getRunningVehicles();
        return CommonResponse.success(response);
    }

}
