package com.hyundai.crawldemo.domain.exception;

import org.springframework.http.HttpStatus;

public abstract class HttpStatusException extends RuntimeException {

  public HttpStatusException(String message) {
    super(message);
  }

  public abstract HttpStatus getHttpStatus();

}
