package com.hyundai.crawldemo.domain.crawl.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hyundai.crawldemo.domain.crawl.constant.CrawlConstant;
import com.hyundai.crawldemo.domain.crawl.exception.InvalidUrlException;
import com.hyundai.crawldemo.domain.crawl.model.CrawlResult;
import com.hyundai.crawldemo.domain.crawl.port.CrawlService;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrawlServiceImplTest {

  @Autowired
  private CrawlService crawlService;

  @Test
  @DisplayName("url 크롤링 test")
  public void crawlTest() throws Exception {
    // given
    String url = "https://www.kia.com";
    URI uri = URI.create(url);

    // when
    CrawlResult result = crawlService.crawl(uri);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getUrl()).isEqualTo(url);
    assertThat(result.getHtml()).isNotBlank();
  }

  @Test
  @DisplayName("url 크롤링 test - 유효한 url이 아닌 경우")
  public void crawlTest_invalidUrl() throws Exception {
    // given
    String url = "https://www.nonexistent.qqq.front";
    URI uri = URI.create(url);

    // when & then
    assertThrows(InvalidUrlException.class, () -> crawlService.crawl(uri));
  }

  @Test
  @DisplayName("url 크롤링 test - uri protocol 정보 (https)가 없는 경우")
  public void crawlTest_withoutScheme() throws Exception {
    // given
    String url = "www.kia.com";
    URI uri = URI.create(url);

    // when
    CrawlResult result = crawlService.crawl(uri);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getUrl()).isEqualTo("https://" + url);
    assertThat(result.getHtml()).isNotBlank();
  }

  @Test
  @DisplayName("병렬 크롤링 test")
  public void crawlConcurrentlyTest() throws Exception {
    // given
    List<String> crawlUrls = CrawlConstant.CRAWL_URLS;
    List<URI> uris = crawlUrls.stream().map(URI::create).toList();

    // when
    List<CrawlResult> results = crawlService.crawlByUris(uris);

    // then
    assertThat(results).isNotEmpty();
    assertThat(crawlUrls.size()).isEqualTo(results.size());
    results.forEach(result -> {
      assertThat(result).isNotNull();
      assertThat(result.getUrl()).isIn(crawlUrls);
      assertThat(result.getHtml()).isNotBlank();
    });
  }

  @Test
  @DisplayName("병렬 크롤링 test - invalid url")
  public void crawlConcurrentlyTest_includeInvalidUrl() throws Exception {
    // given
    String invalidUrl = "https://www.nonexistent.qqq.front";
    List<String> crawlUrls = List.of(CrawlConstant.KIA_URL, invalidUrl);
    List<URI> uris = crawlUrls.stream().map(URI::create).toList();

    // when & then
    assertThrows(InvalidUrlException.class, () -> crawlService.crawlByUris(uris));
  }
}