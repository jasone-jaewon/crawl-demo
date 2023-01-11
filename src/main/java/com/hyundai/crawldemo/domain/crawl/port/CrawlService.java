package com.hyundai.crawldemo.domain.crawl.port;

import java.net.URI;
import java.util.List;

public interface CrawlService {

  /**
   * url 에 해당하는 html 정보 crawling
   *
   * @param url crawling 할 url
   * @return html string
   */
  String crawl(URI url);

  /**
   * url 에 해당하는 html 정보 crawling(병렬)
   *
   * @param urls crawling 할 url 목록
   * @return html 목록
   */
  List<String> crawlByUris(List<URI> urls);

}
