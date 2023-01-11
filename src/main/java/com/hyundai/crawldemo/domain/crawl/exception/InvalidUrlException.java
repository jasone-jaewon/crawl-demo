package com.hyundai.crawldemo.domain.crawl.exception;

import com.hyundai.crawldemo.domain.exception.BadRequestException;

public class InvalidUrlException extends BadRequestException {

  public InvalidUrlException(String url) {
    super("url 형식이 올바르지 않습니다. url: " + url);
  }
}
