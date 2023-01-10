package com.hyundai.crawldemo.domain.crawl.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hyundai.crawldemo.domain.crawl.constant.CrawlConstant;
import com.hyundai.crawldemo.domain.crawl.exception.CrawlFailException;
import com.hyundai.crawldemo.domain.crawl.port.CrawlService;
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

    // when
    String html = crawlService.crawl(url);

    // then
    assertThat(html).isNotBlank();
  }

  @Test
  @DisplayName("url 크롤링 test - 유효한 url이 아닌 경우")
  public void crawlTest_invalidUrl() throws Exception {
    // given
    String url = "https://www.nonexistent.qqq.front";

    // when & then
    assertThrows(CrawlFailException.class, () -> crawlService.crawl(url));
  }

  @Test
  @DisplayName("병렬 크롤링 test")
  public void crawlConcurrentlyTest() throws Exception {
    // given
    List<String> crawlUrls = CrawlConstant.CRAWL_URLS;

    // when
    List<String> htmlList = crawlService.crawlByUrls(crawlUrls);

    // then
    assertThat(crawlUrls.size()).isEqualTo(htmlList.size());
    htmlList.forEach(html -> assertThat(html).isNotBlank());
  }

  @Test
  @DisplayName("병렬 크롤링 test - 크롤링 실패한 경우")
  public void crawlConcurrentlyTest_includeInvalidUrl() throws Exception {
    // given
    String invalidUrl = "https://www.nonexistent.qqq.front";
    List<String> crawlUrls = List.of(CrawlConstant.KIA_URL, invalidUrl);

    // when & then
    assertThrows(CrawlFailException.class, () -> crawlService.crawlByUrls(crawlUrls));
  }
}