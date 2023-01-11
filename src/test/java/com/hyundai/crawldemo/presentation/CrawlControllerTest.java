package com.hyundai.crawldemo.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CrawlControllerTest {

  private static final String CRAWL_API_PATH = "/v1/crawl";

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("크롤링 api test - 유효하지 않은 url 포함한 경우")
  public void crawlWebSitesTest_invalidUrl() throws Exception {
    // given
    String invalidUrl = "https://invalid.test.com.net.com.net";
    // when
    mockMvc.perform(get(CRAWL_API_PATH)
            .param("urls", invalidUrl))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("result").exists())
    ;
  }

  @Test
  @DisplayName("크롤링 api test - 파라미터로 url 정보 넘기는 경우")
  public void crawlWebSitesTest_url() throws Exception {
    // given
    List<String> urls = List.of("https://www.naver.com", "https://www.google.com");

    // when
    mockMvc.perform(get(CRAWL_API_PATH)
            .param("urls", String.join(",", urls)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("result").exists())
    ;
  }

  @Test
  @DisplayName("크롤링 api test - default urls")
  public void crawlWebSitesTest_defaultUrl() throws Exception {
    // when
    mockMvc.perform(get(CRAWL_API_PATH))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("result").exists())
    ;
  }
}