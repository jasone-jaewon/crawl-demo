package com.hyundai.crawldemo.domain.alphanumeric.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class Alphanumeric {

  private final List<Character> upperCases;
  private final List<Character> lowerCases;
  private final List<Character> numbers;

  /**
   * 생성 method - 각 문자를 대문자, 소문자, 숫자로 구분하여 멤버변수로 저장
   *
   * @param input 문자열
   * @return Alphanumeric
   */
  public static Alphanumeric createByString(String input) {
    if (!StringUtils.hasText(input)) {
      return new Alphanumeric(
          Collections.emptyList(),
          Collections.emptyList(),
          Collections.emptyList()
      );
    }

    Set<Character> upperCases = new HashSet<>();
    Set<Character> lowerCases = new HashSet<>();
    Set<Character> numbers = new HashSet<>();

    char[] chars = input.toCharArray();
    for (char character : chars) {
      if (Character.isUpperCase(character)) {
        upperCases.add(character);
        continue;
      }
      if (Character.isLowerCase(character)) {
        lowerCases.add(character);
        continue;
      }
      if (Character.isDigit(character)) {
        numbers.add(character);
      }
    }

    // TODO: 2023/01/11 sorted 효율 수정
    return new Alphanumeric(
        upperCases.stream().sorted().toList(),
        lowerCases.stream().sorted().toList(),
        numbers.stream().sorted().toList()
    );
  }

  /**
   * 각 목록 정렬
   */
  public void sort() {
    Collections.sort(this.upperCases);
    Collections.sort(this.lowerCases);
    Collections.sort(this.numbers);
  }

  /**
   * 목록 병합 - 대문자, 소문자, 숫자 순으로 병합
   *
   * @return 병합된 문자열
   */
  public String merge() {
    StringBuilder builder = new StringBuilder();
    int maxLength = Math.max(this.lowerCases.size(), this.upperCases.size());
    maxLength = Math.max(maxLength, this.numbers.size());

    for (int i = 0; i < maxLength; i++) {
      if (this.upperCases.size() > i) {
        builder.append(this.upperCases.get(i));
      }
      if (this.lowerCases.size() > i) {
        builder.append(this.lowerCases.get(i));
      }
      if (this.numbers.size() > i) {
        builder.append(this.numbers.get(i));
      }
    }

    return builder.toString();
  }
}
