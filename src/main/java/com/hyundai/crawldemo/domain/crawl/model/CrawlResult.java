package com.hyundai.crawldemo.domain.crawl.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CrawlResult {
  private final String url;
  private final String html;
}
