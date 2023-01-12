package com.hyundai.crawldemo.application.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hyundai.crawldemo.application.cache.port.CacheService;
import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import com.hyundai.crawldemo.domain.alphanumeric.port.AlphanumericService;
import com.hyundai.crawldemo.domain.crawl.model.CrawlResult;
import com.hyundai.crawldemo.domain.crawl.port.CrawlService;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

  @Mock
  private CacheService cacheService;

  @Test
  @DisplayName("uri 목록에 해당하는 html alphanumeric 정보 추출 test")
  public void getAlphanumericFromUrisTest() throws Exception {
    // given
    Set<Character> expectedUpperCases = Set.of('C', 'H', 'K');
    Set<Character> expectedLowerCases = Set.of('a', 'b', 'c', 'd', 'h', 'i', 'k', 'l', 'm', 'n',
        'o', 'r', 's', 't', 'u', 'y');
    Set<Character> expectedNumbers = Set.of('7', '8', '9');

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

    when(cacheService.getValue(uri1.toString(), Alphanumeric.class)).thenReturn(
        Optional.of(alphanumeric1));
    when(crawlService.crawl(uri2)).thenReturn(crawlResult2);
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

    // when
    Alphanumeric alphanumeric = htmlAlphanumericService.getAlphanumericFromUris(uris);

    // then
    verify(crawlService, never()).crawl(any());

    assertThat(alphanumeric).isNotNull();
    assertThat(alphanumeric.getUpperCases()).isEmpty();
    assertThat(alphanumeric.getLowerCases()).isEmpty();
    assertThat(alphanumeric.getNumbers()).isEmpty();
  }

  @Test
  @DisplayName("uri 목록에 해당하는 html alphanumeric 정보 추출 test - 캐싱되는 경우")
  public void getAlphanumericFromUrisTest_cache() throws Exception {
    // given
    URI uri1 = URI.create("https://www.kia.com");
    URI uri2 = URI.create("https://www.hyundai.com");
    List<URI> uris = List.of(uri1, uri2);

    Alphanumeric alphanumeric1 = Alphanumeric.createByString("<html>Kia car k9 k8 k7</html>");
    Alphanumeric alphanumeric2 = Alphanumeric.createByString("<html>Hyundai Car</html>");

    when(cacheService.getValue(uri1.toString(), Alphanumeric.class)).thenReturn(
        Optional.of(alphanumeric1));
    when(cacheService.getValue(uri2.toString(), Alphanumeric.class)).thenReturn(
        Optional.of(alphanumeric2));

    // when
    htmlAlphanumericService.getAlphanumericFromUris(uris);

    // then
    verify(crawlService, never()).crawl(any());
    verify(alphanumericService, never()).parse(any());
  }

  @Test
  @DisplayName("uri 목록에 해당하는 html alphanumeric 정보 추출 test - 캐싱 안되는 경우")
  public void getAlphanumericFromUrisTest_notCache() throws Exception {
    // given
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

    when(cacheService.getValue(uri1.toString(), Alphanumeric.class)).thenReturn(Optional.empty());
    when(cacheService.getValue(uri2.toString(), Alphanumeric.class)).thenReturn(Optional.empty());
    when(crawlService.crawl(uri1)).thenReturn(crawlResult1);
    when(crawlService.crawl(uri2)).thenReturn(crawlResult2);
    when(alphanumericService.parse(crawlResult1)).thenReturn(alphanumeric1);
    when(alphanumericService.parse(crawlResult2)).thenReturn(alphanumeric2);

    // when
    htmlAlphanumericService.getAlphanumericFromUris(uris);

    // then
    verify(crawlService, times(uris.size())).crawl(any());
    verify(alphanumericService, times(uris.size())).parse(any());
    verify(cacheService, times(uris.size())).setValue(any(), any());
  }
}