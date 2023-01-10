package com.hyundai.crawldemo.domain.crawl.impl;

import com.hyundai.crawldemo.domain.crawl.exception.CrawlFailException;
import com.hyundai.crawldemo.domain.crawl.port.CrawlService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CrawlServiceImpl implements CrawlService {

  /**
   * url 에 해당하는 html 정보 crawling
   *
   * @param url crawling 할 url
   * @return html string
   */
  @Override
  public String crawl(String url) {
    try {
      Document document = Jsoup.connect(url).get();
      return document.toString();
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new CrawlFailException(url);
    }
  }
}
