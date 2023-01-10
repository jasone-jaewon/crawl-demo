package com.hyundai.crawldemo.domain.crawl.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hyundai.crawldemo.domain.crawl.exception.CrawlFailException;
import com.hyundai.crawldemo.domain.crawl.port.CrawlService;
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
}