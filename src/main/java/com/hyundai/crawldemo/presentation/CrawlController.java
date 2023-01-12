package com.hyundai.crawldemo.presentation;

import com.hyundai.crawldemo.application.port.HtmlAlphanumericService;
import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import com.hyundai.crawldemo.domain.crawl.constant.CrawlConstant;
import com.hyundai.crawldemo.presentation.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/crawl")
public class CrawlController {

  private final HtmlAlphanumericService htmlAlphanumericService;

  /**
   * 웹사이트 크롤링 api
   *
   * @param urls 크롤링할 url 목록
   * @return 크롤링하여 파싱한 정보 response
   */
  @Operation(summary = "웹사이트 크롤링", description = "웹사이트를 크롤링하여 알파벳,숫자만 추출하여 결과값 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "정상"),
      @ApiResponse(responseCode = "400", description = "invalid url"),
      @ApiResponse(responseCode = "500", description = "Server Error"),
  })
  @GetMapping
  public CommonResponse<String> crawlWebSites(@RequestParam(required = false) List<String> urls) {
    if (CollectionUtils.isEmpty(urls)) {
      urls = CrawlConstant.CRAWL_URLS;
    }
    List<URI> uris = urls.stream().map(URI::create).toList();
    Alphanumeric alphanumeric = htmlAlphanumericService.getAlphanumericFromUris(uris);
    return CommonResponse.success(alphanumeric.mergeToString());
  }
}
