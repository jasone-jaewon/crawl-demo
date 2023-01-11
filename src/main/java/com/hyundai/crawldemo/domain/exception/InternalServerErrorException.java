package com.hyundai.crawldemo.domain.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends HttpStatusException {
  private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  public InternalServerErrorException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
