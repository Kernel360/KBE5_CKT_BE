package kernel360.ckt.core.domain.entity;

import jakarta.persistence.*;
import kernel360.ckt.core.domain.enums.FuelType;
import kernel360.ckt.core.domain.enums.TransmissionType;
import kernel360.ckt.core.domain.enums.VehicleStatus;
import kernel360.ckt.core.domain.persistence.BooleanToYNConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vehicle")
@Entity
public class VehicleEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @Column(nullable = false, length = 10)
    private String registrationNumber;

    @Column(length = 4)
    private String modelYear;

    @Column(length = 30)
    private String manufacturer;

    @Column(length = 30)
    private String modelName;

    @Column(length = 10)
    private String batteryVoltage;

    @Enumerated(EnumType.STRING)
    @Column
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column
    private TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    @Column
    private VehicleStatus status;

    @Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
    private Double lat;

    @Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
    private Double lon;

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long odometer;

    @Lob
    private String memo;

    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1)")
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean deleteYn = false;

    private VehicleEntity(CompanyEntity company, String registrationNumber, String modelYear, String manufacturer, String modelName,
                          String batteryVoltage, FuelType fuelType, TransmissionType transmissionType,
                          VehicleStatus status, String memo) {
        this.company = company;
        this.registrationNumber = registrationNumber;
        this.modelYear = modelYear;
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.batteryVoltage = batteryVoltage;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.status = status;
        this.memo = memo;
    }

    public static VehicleEntity create(CompanyEntity company, String registrationNumber, String modelYear, String manufacturer, String modelName,
                                       String batteryVoltage, FuelType fuelType, TransmissionType transmissionType,
                                       VehicleStatus status, String memo) {
        return new VehicleEntity(company, registrationNumber, modelYear, manufacturer, modelName,
            batteryVoltage, fuelType, transmissionType, status, memo);
    }

    public void updateVehicle(String modelYear, String manufacturer, String modelName,
        String batteryVoltage, FuelType fuelType, TransmissionType transmissionType,
        String memo
    ) {
        this.modelYear = modelYear;
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.batteryVoltage = batteryVoltage;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.memo = memo;
    }

    public void updateLocation(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public void updateOdometer(Long totalDistance) {
        final long currentOdometer = (this.odometer != null) ? this.odometer : 0L;
        this.odometer = currentOdometer + totalDistance;
    }

    public void delete() {
        this.deleteYn = true;
    }
}
