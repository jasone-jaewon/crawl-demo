package com.hyundai.crawldemo.domain.crawl.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.hyundai.crawldemo.domain.util.StringUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringUtilTest {

  @Test
  @DisplayName("알파벳 숫자 추출 test")
  public void extractAlphanumericTest() throws Exception {
    // given
    String input = "rt$#@!5869ABEE테스트뷁p0 g!!1";
    String expected = "rt5869ABEEp0g1";

    // when
    String extracted = StringUtil.extractAlphanumeric(input);

    // then
    assertThat(extracted).isEqualTo(expected);
  }


  @Test
  @DisplayName("알파벳 숫자 추출 test - null")
  public void extractAlphanumericTest_null() throws Exception {
    // given
    String input = null;

    // when
    String extracted = StringUtil.extractAlphanumeric(input);

    // then
    assertThat(extracted).isEqualTo(input);
  }
}