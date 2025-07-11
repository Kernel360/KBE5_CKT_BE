package kernel360.ckt.admin.application.port;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kernel360.ckt.core.domain.entity.RentalEntity;
import kernel360.ckt.core.domain.entity.VehicleEntity;
import kernel360.ckt.core.domain.enums.RentalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 차량 대여(Rental)에 대한 저장소 역할을 하는 포트 인터페이스입니다.
 * 인프라 계층에서 해당 인터페이스를 구현하여 JPA 또는 다른 방식으로 데이터 접근을 수행합니다.
 */
public interface RentalRepository {

    /**
     * 대여 정보를 저장하거나 수정합니다.
     *
     * @param rental 저장할 대여 엔티티
     * @return 저장된 대여 엔티티
     */
    RentalEntity save(RentalEntity rental);

    /**
     * 조건에 따라 대여 목록을 페이징 조회합니다.
     *
     * @param companyId 회사 ID (null 허용)
     * @param status 대여 상태 (null 허용)
     * @param keyword 사용자 이름 또는 전화번호 검색 키워드 (null 허용)
     * @param startAt 대여 시작일 조건 (null 허용)
     * @param endAt 대여 종료일 조건 (null 허용)
     * @param pageable 페이징 정보
     * @return 조건에 맞는 대여 목록
     */
    Page<RentalEntity> findAll(
        Long companyId,
        RentalStatus status,
        String keyword,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Pageable pageable
    );

    /**
     * 대여 ID로 대여 정보를 조회합니다.
     *
     * @param id 대여 ID
     * @return 존재할 경우 대여 엔티티, 존재하지 않을 경우 빈 Optional 객체
     */
    Optional<RentalEntity> findById(Long id);

    /**
     * 특정 차량, 상태, 시간 기준으로 겹치는 렌탈 정보를 조회합니다.
     * @param vehicle 대상 차량
     * @param statuses 포함할 렌탈 상태 목록
     * @param pickupAt 픽업 시간
     * @param returnAt 반납 시간
     * @return 조건에 부합하는 렌탈 목록
     */
    List<RentalEntity> findOverlappingRentalsByVehicleAndStatusesExcludingRental(
        VehicleEntity vehicle,
        List<RentalStatus> statuses,
        LocalDateTime pickupAt,
        LocalDateTime returnAt,
        Long excludeRentalId
    );

    /**
     * 현재 대여 중(RENTED 상태)인 고객 수를 조회합니다.
     * 동일 고객이 여러 대여건을 가지고 있어도 1명으로 계산합니다.
     *
     * @return 대여 중인 고객 수
     */
    long countRentedCustomers();

    /**
     * 특정 고객의 최신 대여 정보를 상태 조건과 함께 조회합니다.
     * 차량 정보도 함께 조회(fetch join)합니다.
     *
     * @param customerId 고객 ID
     * @param status 조회할 대여 상태
     * @return 조건에 해당하는 최신 대여 정보 (없으면 Optional.empty)
     */
    Optional<RentalEntity> findLatestRentalByCustomerIdAndStatus(Long customerId, RentalStatus status);

    /**
     * 특정 고객의 전체 대여 이력을 최신순으로 조회합니다.
     * 각 대여건의 차량 정보도 함께 조회(fetch join)합니다.
     *
     * @param customerId 고객 ID
     * @return 해당 고객의 대여 이력 목록
     */
    List<RentalEntity> findAllByCustomerIdFetchVehicle(Long customerId);

    /**
     * 특정 고객의 전체 대여 건수를 조회합니다.
     *
     * @param customerId 고객 ID
     * @return 전체 대여 횟수
     */
    long countByCustomerId(Long customerId);

    /**
     * 특정 고객이 특정 상태(RENTED 등)로 가지고 있는 대여 건수를 조회합니다.
     *
     * @param customerId 고객 ID
     * @param status 조회할 대여 상태
     * @return 조건에 맞는 대여 건수
     */
    long countByCustomerIdAndStatus(Long customerId, RentalStatus status);

    /**
     * 특정 회사의 현재 대여 중(RENTED 상태)인 고객 수를 조회합니다.
     * 동일 고객이 여러 대여건을 가지고 있어도 1명으로 계산합니다.
     *
     * @param companyId 회사 ID
     * @return 해당 회사의 대여 중인 고객 수
     */
    long countRentedCustomersByCompanyId(Long companyId);
}
