package com.hyundai.crawldemo.domain.crawl.port;

public interface CrawlService {

  /**
   * url 에 해당하는 html 정보 crawling
   *
   * @param url crawling 할 url
   * @return html string
   */
  String crawl(String url);

}
