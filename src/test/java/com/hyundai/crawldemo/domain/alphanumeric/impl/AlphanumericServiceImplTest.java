package com.hyundai.crawldemo.domain.alphanumeric.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import com.hyundai.crawldemo.domain.crawl.model.CrawlResult;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlphanumericServiceImplTest {

  @InjectMocks
  private AlphanumericServiceImpl alphanumericService;

  @Test
  @DisplayName("알파벳, 숫자 문자열 파싱 test - 중복제거, 알파벳 추출")
  public void parseTest() throws Exception {
    // given
    String html = "<html>Bf3&&gfWEa668>>>!@#!@#>>>8</html>";
    CrawlResult crawlResult = new CrawlResult("www.test.com", html);
    List<Character> expectedUpperCases = List.of('B', 'E', 'W');
    List<Character> expectedLowerCases = List.of('a', 'f', 'g', 'h', 'l', 'm', 't');
    List<Character> expectedNumbers = List.of('3', '6', '8');

    // when
    Alphanumeric alphanumeric = alphanumericService.parse(crawlResult);

    // then
    assertThat(alphanumeric).isNotNull();
    assertThat(alphanumeric.getUpperCases()).isEqualTo(expectedUpperCases);
    assertThat(alphanumeric.getLowerCases()).isEqualTo(expectedLowerCases);
    assertThat(alphanumeric.getNumbers()).isEqualTo(expectedNumbers);
  }
}