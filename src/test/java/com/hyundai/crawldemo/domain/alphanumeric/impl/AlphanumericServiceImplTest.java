package com.hyundai.crawldemo.domain.alphanumeric.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlphanumericServiceImplTest {

  @InjectMocks
  private AlphanumericServiceImpl alphanumericService;

  @Test
  @DisplayName("알파벳, 숫자 문자열 파싱 test - 중복제거, 정렬")
  public void parseTest() throws Exception {
    // given
    List<String> inputs = List.of("Bf3gfWEa6688", "gie51f503QFVDDR", "gjaodpq123");
    List<Character> expectedUpperCases = List.of('B', 'D', 'E', 'F', 'Q', 'R', 'V', 'W');
    List<Character> expectedLowerCases = List.of('a', 'd', 'e', 'f', 'g', 'i', 'j', 'o', 'p', 'q');
    List<Character> expectedNumbers = List.of('0', '1', '2', '3', '5', '6', '8');

    // when
    Alphanumeric alphanumeric = alphanumericService.parse(inputs);

    // then
    assertThat(alphanumeric).isNotNull();
    assertThat(alphanumeric.getUpperCases()).isEqualTo(expectedUpperCases);
    assertThat(alphanumeric.getLowerCases()).isEqualTo(expectedLowerCases);
    assertThat(alphanumeric.getNumbers()).isEqualTo(expectedNumbers);
  }
}