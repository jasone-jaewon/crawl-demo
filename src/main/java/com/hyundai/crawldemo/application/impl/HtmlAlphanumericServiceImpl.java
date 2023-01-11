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

  private final AlphanumericService alphanumericService;
  private final CrawlService crawlService;

  @Override
  public Alphanumeric getAlphanumericFromUris(List<URI> uris) {
    List<CrawlResult> crawlResults = crawlService.crawlByUris(uris);

    List<Alphanumeric> alphanumerics = crawlResults.stream()
        .map(alphanumericService::parse)
        .toList();

    return Alphanumeric.merge(alphanumerics);
  }
}
