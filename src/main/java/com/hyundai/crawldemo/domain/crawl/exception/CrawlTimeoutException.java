package com.hyundai.crawldemo.domain.crawl.exception;

import com.hyundai.crawldemo.domain.exception.ServiceUnavailableException;

public class CrawlTimeoutException extends ServiceUnavailableException {

  public CrawlTimeoutException(String url) {
    super("크롤링 도중 타임 아웃이 발생하였습니다. url: " + url);
  }
}
