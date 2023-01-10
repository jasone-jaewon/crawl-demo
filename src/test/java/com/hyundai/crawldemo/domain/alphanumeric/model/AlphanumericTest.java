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
  @DisplayName("정렬 test")
  public void sortTest() throws Exception {
    // given
    String input = "dEaG28A5Fk1";
    List<Character> expectedLowerCases = List.of('a', 'd', 'k');
    List<Character> expectedUpperCases = List.of('A', 'E', 'F', 'G');
    List<Character> expectedNumbers = List.of('1', '2', '5', '8');
    Alphanumeric alphanumeric = Alphanumeric.createByString(input);

    // when
    alphanumeric.sort();

    // then
    assertThat(alphanumeric).isNotNull();
    assertThat(alphanumeric.getUpperCases()).isEqualTo(expectedUpperCases);
    assertThat(alphanumeric.getLowerCases()).isEqualTo(expectedLowerCases);
    assertThat(alphanumeric.getNumbers()).isEqualTo(expectedNumbers);
  }

  @Test
  @DisplayName("병합 Test")
  public void mergeTest() throws Exception {
    // given
    String input = "dEaG28A5Fk1Q";
    String expected = "Aa1Ed2Fk5G8Q";
    Alphanumeric alphanumeric = Alphanumeric.createByString(input);
    alphanumeric.sort();

    // when
    String merge = alphanumeric.merge();

    // then
    assertThat(merge).isEqualTo(expected);
  }
}