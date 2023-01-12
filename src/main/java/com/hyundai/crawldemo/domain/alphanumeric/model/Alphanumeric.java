package com.hyundai.crawldemo.domain.alphanumeric.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Alphanumeric {

  private final Set<Character> upperCases;
  private final Set<Character> lowerCases;
  private final Set<Character> numbers;

  /**
   * 생성 method - 각 문자를 대문자, 소문자, 숫자로 구분하여 멤버변수로 저장
   *
   * @param input 문자열
   * @return Alphanumeric
   */
  public static Alphanumeric createByString(String input) {
    if (!StringUtils.hasText(input)) {
      return new Alphanumeric(
          Collections.emptySet(),
          Collections.emptySet(),
          Collections.emptySet()
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

    return new Alphanumeric(upperCases, lowerCases, numbers);
  }

  /**
   * 서로 다른 알파벳 숫자 객체 병합
   *
   * @param alphanumerics 알파벳 숫자 객체 목록
   * @return 병합된 알파벳 숫자 객체
   */
  public static Alphanumeric merge(List<Alphanumeric> alphanumerics) {
    Set<Character> upperCases = new HashSet<>();
    Set<Character> lowerCases = new HashSet<>();
    Set<Character> numbers = new HashSet<>();
    alphanumerics.forEach(alphanumeric -> {
      upperCases.addAll(new HashSet<>(alphanumeric.getUpperCases()));
      lowerCases.addAll(new HashSet<>(alphanumeric.getLowerCases()));
      numbers.addAll(new HashSet<>(alphanumeric.getNumbers()));
    });

    return new Alphanumeric(upperCases, lowerCases, numbers);
  }

  /**
   * 목록 병합 - 대문자, 소문자, 숫자 순으로 병합
   *
   * @return 병합된 문자열
   */
  public String mergeToString() {
    StringBuilder builder = new StringBuilder();
    int maxLength = Math.max(this.lowerCases.size(), this.upperCases.size());
    maxLength = Math.max(maxLength, this.numbers.size());

    List<Character> upperCaseList = new ArrayList<>(this.upperCases);
    Collections.sort(upperCaseList);

    List<Character> lowerCaseList = new ArrayList<>(this.lowerCases);
    Collections.sort(lowerCaseList);

    List<Character> numberList = new ArrayList<>(this.numbers);
    Collections.sort(numberList);

    for (int i = 0; i < maxLength; i++) {
      if (upperCaseList.size() > i) {
        builder.append(upperCaseList.get(i));
      }
      if (lowerCaseList.size() > i) {
        builder.append(lowerCaseList.get(i));
      }
      if (numberList.size() > i) {
        builder.append(numberList.get(i));
      }
    }

    return builder.toString();
  }
}
