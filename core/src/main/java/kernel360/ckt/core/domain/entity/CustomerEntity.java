package kernel360.ckt.core.domain.entity;

import jakarta.persistence.*;
import kernel360.ckt.core.domain.enums.CustomerStatus;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customer")
@Entity
public class CustomerEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String licenseNumber;
    private String zipCode;
    private String address;
    private String detailedAddress;
    private String birthday;

    @Lob
//    @Column(columnDefinition = "Text")
    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;

    public CustomerEntity(String customerName,
                          String phoneNumber,
                          String licenseNumber,
                          String zipCode,
                          String address,
                          String detailedAddress,
                          String birthday,
                          String memo,
                          CustomerStatus status) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.zipCode = zipCode;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.birthday = birthday;
        this.memo = memo;
        this.status = status;
    }

    public static CustomerEntity create(
        String customerName,
        String phoneNumber,
        String licenseNumber,
        String zipCode,
        String address,
        String detailedAddress,
        String birthday,
        String memo
    ) {
        return new CustomerEntity(
            customerName,
            phoneNumber,
            licenseNumber,
            zipCode,
            address,
            detailedAddress,
            birthday,
            memo,
            CustomerStatus.ACTIVE
        );
    }

    public void updateBasicInfo(String customerName,
                                String phoneNumber,
                                String licenseNumber,
                                String zipCode,
                                CustomerStatus status,
                                String address,
                                String detailedAddress,
                                String birthday,
                                String memo) {
                                                this.customerName = customerName;
                                                this.phoneNumber = phoneNumber;
                                                this.licenseNumber = licenseNumber;
                                                this.zipCode = zipCode;
                                                this.status = status;
                                                this.address = address;
                                                this.detailedAddress = detailedAddress;
                                                this.birthday = birthday;
                                                this.memo = memo;
                                            }

}
