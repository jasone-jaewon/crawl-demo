package com.hyundai.crawldemo.domain.alphanumeric.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlphanumericTest {

  @Test
  @DisplayName("생성자 test")
  public void constructorTest() throws Exception {
    // given
    String input = "aAdE125FGk8";
    List<Character> expectedLowerCases = List.of('a', 'd', 'k');
    List<Character> expectedUpperCases = List.of('A', 'E', 'F', 'G');
    List<Character> expectedNumbers = List.of('1', '2', '5', '8');

    // when
    Alphanumeric alphanumeric = Alphanumeric.createByString(input);

    // then
    assertThat(alphanumeric).isNotNull();
    assertThat(alphanumeric.getUpperCases()).isEqualTo(expectedUpperCases);
    assertThat(alphanumeric.getLowerCases()).isEqualTo(expectedLowerCases);
    assertThat(alphanumeric.getNumbers()).isEqualTo(expectedNumbers);
  }

  @Test
  @DisplayName("생성자 test - input null")
  public void constructorTest_null() throws Exception {
    // given
    String input = null;

    // when
    Alphanumeric alphanumeric = Alphanumeric.createByString(input);

    // then
    assertThat(alphanumeric).isNotNull();
    assertThat(alphanumeric.getUpperCases()).isEmpty();
    assertThat(alphanumeric.getLowerCases()).isEmpty();
    assertThat(alphanumeric.getNumbers()).isEmpty();
  }

  @Test
  @DisplayName("병합 test")
  public void mergeTest() throws Exception {
    // given
    Alphanumeric alphanumeric1 = Alphanumeric.createByString("aAdE125FGk8");
    Alphanumeric alphanumeric2 = Alphanumeric.createByString("fEThNV5FGgeoi120");
    List<Character> expectedUpperCases = List.of('A', 'E', 'F', 'G', 'N', 'T', 'V');
    List<Character> expectedLowerCases = List.of('a', 'd', 'e', 'f', 'g', 'h', 'i', 'k', 'o');
    List<Character> expectedNumbers = List.of('0', '1', '2', '5', '8');


    // when
    Alphanumeric alphanumeric = Alphanumeric.merge(List.of(alphanumeric1, alphanumeric2));

    // then
    assertThat(alphanumeric).isNotNull();
    assertThat(alphanumeric.getUpperCases()).isEqualTo(expectedUpperCases);
    assertThat(alphanumeric.getLowerCases()).isEqualTo(expectedLowerCases);
    assertThat(alphanumeric.getNumbers()).isEqualTo(expectedNumbers);
  }


  @Test
  @DisplayName("문자열 병합 Test")
  public void mergeToStringTest() throws Exception {
    // given
    String input = "dEaG28A5Fk1Q";
    String expected = "Aa1Ed2Fk5G8Q";
    Alphanumeric alphanumeric = Alphanumeric.createByString(input);

    // when
    String merge = alphanumeric.mergeToString();

    // then
    assertThat(merge).isEqualTo(expected);
  }

  @Test
  @DisplayName("문자열 병합 Test - 빈 객체")
  public void mergeToStringTest_emptyString() throws Exception {
    // given
    String input = null;
    String expected = "";
    Alphanumeric alphanumeric = Alphanumeric.createByString(input);

    // when
    String merge = alphanumeric.mergeToString();

    // then
    assertThat(merge).isEqualTo(expected);
  }
}