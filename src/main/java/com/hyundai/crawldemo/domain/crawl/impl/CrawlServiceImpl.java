package com.hyundai.crawldemo.domain.crawl.impl;

import com.hyundai.crawldemo.domain.crawl.exception.CrawlFailException;
import com.hyundai.crawldemo.domain.crawl.exception.CrawlTimeoutException;
import com.hyundai.crawldemo.domain.crawl.exception.InvalidUrlException;
import com.hyundai.crawldemo.domain.crawl.model.CrawlResult;
import com.hyundai.crawldemo.domain.crawl.port.CrawlService;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlServiceImpl implements CrawlService {

  private static final String DEFAULT_SCHEME = "https://";

  /**
   * url 에 해당하는 html 정보 crawling
   *
   * @param uri crawling 할 uri
   * @return html string
   */
  @Retryable(
      retryFor = CrawlTimeoutException.class,
      maxAttempts = 2,
      backoff = @Backoff(500)
  )
  @Override
  public CrawlResult crawl(URI uri) {
    String url = StringUtils.hasText(uri.getScheme()) ? uri.toString() : DEFAULT_SCHEME + uri;
    log.info("crawl url: " + url);
    try {
      Document document = Jsoup.connect(url).get();
      return new CrawlResult(url, document.toString());
    } catch (UnknownHostException e) {
      throw new InvalidUrlException(url);
    } catch (SocketTimeoutException e) {
      throw new CrawlTimeoutException(url);
    } catch (IOException e) {
      throw new CrawlFailException(url);
    }
  }
}
