package com.hyundai.crawldemo.application.impl;

import com.hyundai.crawldemo.application.port.HtmlAlphanumericService;
import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import com.hyundai.crawldemo.domain.alphanumeric.port.AlphanumericService;
import com.hyundai.crawldemo.application.cache.port.CacheService;
import com.hyundai.crawldemo.domain.crawl.model.CrawlResult;
import com.hyundai.crawldemo.domain.crawl.port.CrawlService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HtmlAlphanumericServiceImpl implements HtmlAlphanumericService {

  private final CacheService cacheService;
  private final AlphanumericService alphanumericService;
  private final CrawlService crawlService;

  @Override
  public Alphanumeric getAlphanumericFromUris(List<URI> uris) {
    List<URI> notCachedUris = new ArrayList<>();
    List<Alphanumeric> alphanumerics = new ArrayList<>();

    uris.forEach(uri -> cacheService.getValue(uri.toString(), Alphanumeric.class)
        .ifPresentOrElse(alphanumerics::add, () -> notCachedUris.add(uri))
    );

    List<CrawlResult> crawlResults = notCachedUris.parallelStream()
        .map(crawlService::crawl)
        .toList();

    crawlResults.forEach(crawlResult -> {
      Alphanumeric parsedAlphanumeric = alphanumericService.parse(crawlResult);
      alphanumerics.add(parsedAlphanumeric);
      cacheService.setValue(crawlResult.getUrl(), parsedAlphanumeric);
    });

    return Alphanumeric.merge(alphanumerics);
  }
}
