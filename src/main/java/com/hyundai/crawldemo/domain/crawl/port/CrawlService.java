package com.hyundai.crawldemo.domain.crawl.port;

import com.hyundai.crawldemo.domain.crawl.model.CrawlResult;
import java.net.URI;
import java.util.List;

public interface CrawlService {

  /**
   * url 에 해당하는 html 정보 crawling
   *
   * @param url crawling 할 url
   * @return html string
   */
  CrawlResult crawl(URI url);
}
