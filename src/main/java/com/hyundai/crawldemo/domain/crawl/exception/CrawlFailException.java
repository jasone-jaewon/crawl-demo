package com.hyundai.crawldemo.domain.crawl.exception;

import com.hyundai.crawldemo.domain.exception.InternalServerErrorException;

public class CrawlFailException extends InternalServerErrorException {

  public CrawlFailException(String url) {
    super("url 에 해당하는 html 정보를 읽지 못했습니다. url: " + url);
  }
}
