package kernel360.ckt.core.common.error;

import lombok.Getter;

@Getter
public enum DrivingLogErrorCode implements ErrorCode {
    DRIVING_LOG_NOT_FOUND("404", "운행일지 정보를 찾을 수 없습니다. (ID - %d)", 500),
    ;
    private final String code;
    private final String message;
    private final int status;

    DrivingLogErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
