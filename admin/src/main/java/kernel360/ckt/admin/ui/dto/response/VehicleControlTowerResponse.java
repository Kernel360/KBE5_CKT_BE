package kernel360.ckt.admin.ui.dto.response;

public record VehicleControlTowerResponse (
    long totalCount,
    long runningCount, // 운행중
    long stoppedCount, // 미운행
    long untrackedCount // 미관제

) {}
