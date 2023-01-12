package com.hyundai.crawldemo.domain.alphanumeric.impl;

import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import com.hyundai.crawldemo.domain.alphanumeric.port.AlphanumericService;
import com.hyundai.crawldemo.domain.crawl.model.CrawlResult;
import com.hyundai.crawldemo.domain.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlphanumericServiceImpl implements AlphanumericService {

  /**
   * 중복 제거된 알파벳과 숫자 목록 객체 파싱
   *
   * @param crawlResult 크롤링 결과
   * @return 대문자, 소문자, 숫자 객체
   */
  @Override
  public Alphanumeric parse(CrawlResult crawlResult) {
    String alphanumericInput = StringUtil.extractAlphanumeric(crawlResult.getHtml());

    return Alphanumeric.createByString(alphanumericInput);
  }
}
