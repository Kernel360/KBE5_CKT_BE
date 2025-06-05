package kernel360.ckt.core.domain.enums;

public enum VehicleControlTowerStatus {
    RUNNING("운행중"),
    STOPPED("미운행"),
    UNTRACKED("미관제");

    private final String description;

    VehicleControlTowerStatus(String description) {
        this.description = description;
    }
}
