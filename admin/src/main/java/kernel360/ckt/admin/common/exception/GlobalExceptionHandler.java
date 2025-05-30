package kernel360.ckt.admin.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import kernel360.ckt.core.common.exception.CustomException;
import kernel360.ckt.core.common.error.ErrorCode;
import kernel360.ckt.core.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String params = getRequestParams(request);

        // ERROR 레벨로 기록
        log.error("⛔️ - URI: {} {}\n- Params: {}\nCustomException: {}",
            method, uri, params, errorCode.getMessage());

        return ResponseEntity.ok(ErrorResponse.from(errorCode));
    }

    private String getRequestParams(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
            .map(entry -> entry.getKey() + "=" + Arrays.toString(entry.getValue()))
            .collect(Collectors.joining(", "));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String params = getRequestParams(request);

        String body = null;
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                body = new String(buf, StandardCharsets.UTF_8);
                body = maskSensitiveData(body);
            }
        }

        log.error("⛔️ - URI: {} {}\n- Params: {}\n- Body: {}\nGeneralException 발생",
            method, uri, params, body, ex);

        return ResponseEntity.ok(
            ErrorResponse.from(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())
        );
    }

    private String maskSensitiveData(String body) {
        if (body == null || body.isBlank()) return body;

        body = body.replaceAll("(\"password\"\\s*:\\s*\").+?(\")", "$1******$2");
        body = body.replaceAll("(\"telNumber\"\\s*:\\s*\")([0-9]{2,3})-([0-9]{3,4})-([0-9]{4})(\")", "$1$2-****-$4$5");

        return body;
    }
}
