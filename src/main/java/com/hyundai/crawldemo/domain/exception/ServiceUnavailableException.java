package com.hyundai.crawldemo.domain.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends HttpStatusException {
  private final HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;

  public ServiceUnavailableException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
