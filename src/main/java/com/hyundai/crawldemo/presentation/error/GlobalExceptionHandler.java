package com.hyundai.crawldemo.presentation.error;

import com.hyundai.crawldemo.domain.exception.HttpStatusException;
import com.hyundai.crawldemo.presentation.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final String SERVER_ERROR_MESSAGE = "요청하신 작업을 수행하지 못했습니다.\n 잠시 후 다시 시도해주세요.";

  @ExceptionHandler({HttpStatusException.class})
  public CommonResponse<String> handleHttpStatusException(HttpStatusException exception) {
    HttpStatus status = exception.getHttpStatus();
    String message = exception.getMessage();
    log.info(message);
    return CommonResponse.fail(status.value(), message);
  }

  @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
  public CommonResponse<String> handleBadRequest(RuntimeException exception) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String message = exception.getMessage();
    log.info(message);
    return CommonResponse.fail(status.value(), message);
  }

  @ExceptionHandler({Exception.class})
  public CommonResponse<String> unHandledException(Exception exception) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    log.error(exception.getMessage());
    return CommonResponse.fail(status.value(), SERVER_ERROR_MESSAGE);
  }
}
