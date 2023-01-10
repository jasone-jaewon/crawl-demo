package com.hyundai.crawldemo.domain.crawl.exception;

public class CrawlFailException extends RuntimeException {

  public CrawlFailException(String url) {
    super("url 에 해당하는 html 정보를 읽지 못했습니다. url: " + url);
  }
}
