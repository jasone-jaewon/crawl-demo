package com.hyundai.crawldemo.domain.util;

import org.springframework.util.StringUtils;

public class StringUtil {

  private static final String ALPHANUMERIC_REGEX = "[^A-Za-z\\d]+";
  private static final String EMPTY_STRING = "";


  /**
   * 알파벳과 숫자만 추출
   *
   * @param input 임의의 문자열
   * @return input에서 알파벳과 숫자만 추출한 결과값
   */
  public static String extractAlphanumeric(String input) {
    if (!StringUtils.hasText(input)) {
      return input;
    }
    return input.replaceAll(ALPHANUMERIC_REGEX, EMPTY_STRING);
  }

  /**
   * 중복 문자 제거
   *
   * @param input 문자열
   * @return 중복 제거된 문자열
   */
  public static String removeDuplicate(String input) {
    if (!StringUtils.hasText(input)) {
      return input;
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < input.length(); i++) {
      char character = input.charAt(i);
      boolean notContainCharacter = builder.toString().indexOf(character) < 0;
      if (notContainCharacter) {
        builder.append(character);
      }
    }

    return builder.toString();
  }
}
