package com.hyundai.crawldemo.application.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import com.hyundai.crawldemo.domain.alphanumeric.port.AlphanumericService;
import com.hyundai.crawldemo.domain.crawl.model.CrawlResult;
import com.hyundai.crawldemo.domain.crawl.port.CrawlService;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HtmlAlphanumericServiceImplTest {

  @InjectMocks
  private HtmlAlphanumericServiceImpl htmlAlphanumericService;

  @Mock
  private CrawlService crawlService;

  @Mock
  private AlphanumericService alphanumericService;

  @Test
  @DisplayName("uri 목록에 해당하는 html alphanumeric 정보 추출 test")
  public void getAlphanumericFromUrisTest() throws Exception {
    // given
    List<Character> expectedUpperCases = List.of('C', 'H', 'K');
    List<Character> expectedLowerCases = List.of('a', 'b', 'c', 'd', 'h', 'i', 'k', 'l', 'm', 'n',
        'o', 'r', 's', 't', 'u', 'y');
    List<Character> expectedNumbers = List.of('7', '8', '9');

    URI uri1 = URI.create("https://www.kia.com");
    URI uri2 = URI.create("https://www.hyundai.com");
    List<URI> uris = List.of(uri1, uri2);
    CrawlResult crawlResult1 = new CrawlResult(uri1.toString(), "<html>Kia car k9 k8 k7</html>");
    CrawlResult crawlResult2 = new CrawlResult(
        uri2.toString(),
        "<html>Hyundai Car sonata hybrid</html>"
    );

    Alphanumeric alphanumeric1 = Alphanumeric.createByString(crawlResult1.getHtml());
    Alphanumeric alphanumeric2 = Alphanumeric.createByString(crawlResult2.getHtml());

    when(crawlService.crawlByUris(uris)).thenReturn(List.of(crawlResult1, crawlResult2));
    when(alphanumericService.parse(crawlResult1)).thenReturn(alphanumeric1);
    when(alphanumericService.parse(crawlResult2)).thenReturn(alphanumeric2);

    // when
    Alphanumeric alphanumeric = htmlAlphanumericService.getAlphanumericFromUris(uris);

    // then
    assertThat(alphanumeric).isNotNull();
    assertThat(alphanumeric.getUpperCases()).isEqualTo(expectedUpperCases);
    assertThat(alphanumeric.getLowerCases()).isEqualTo(expectedLowerCases);
    assertThat(alphanumeric.getNumbers()).isEqualTo(expectedNumbers);
  }

  @Test
  @DisplayName("uri 목록에 해당하는 html alphanumeric 정보 추출 test - empty uri")
  public void getAlphanumericFromUrisTest_emptyUris() throws Exception {
    // given
    List<URI> uris = Collections.emptyList();

    when(crawlService.crawlByUris(uris)).thenReturn(Collections.emptyList());

    // when
    Alphanumeric alphanumeric = htmlAlphanumericService.getAlphanumericFromUris(uris);

    // then
    assertThat(alphanumeric).isNotNull();
    assertThat(alphanumeric.getUpperCases()).isEmpty();
    assertThat(alphanumeric.getLowerCases()).isEmpty();
    assertThat(alphanumeric.getNumbers()).isEmpty();
  }
}