package com.hyundai.crawldemo.domain.crawl.impl;

import com.hyundai.crawldemo.domain.crawl.exception.CrawlFailException;
import com.hyundai.crawldemo.domain.crawl.port.CrawlService;
import java.io.IOException;
import java.net.URI;
import java.util.List;
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
   * @param uri crawling 할 uri
   * @return html string
   */
  @Override
  public String crawl(URI uri) {
    String url = uri.toString();
    try {
      Document document = Jsoup.connect(url).get();
      return document.toString();
    } catch (IOException e) {
      throw new CrawlFailException(url);
    }
  }

  /**
   * url 에 해당하는 html 정보 crawling(병렬)
   *
   * @param uris crawling 할 uri list
   * @return html string
   */
  @Override
  public List<String> crawlByUris(List<URI> uris) {
    return uris.parallelStream()
        .map(this::crawl)
        .toList();
  }
}
