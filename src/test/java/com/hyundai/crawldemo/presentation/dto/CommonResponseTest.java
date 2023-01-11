package com.hyundai.crawldemo.presentation.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonResponseTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("객체 생성 - success")
  public void javaBean_success() throws Exception {
    // given
    String bodyExpected = "test";
    int expected = 200;

    // when
    CommonResponse<String> response = CommonResponse.success(bodyExpected);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatus()).isEqualTo(expected);
    assertThat(response.getResult()).isEqualTo(bodyExpected);
  }

  @Test
  @DisplayName("객체 생성 - fail with message")
  public void javaBean_fail_message() throws Exception {
    // given
    String expectedMessage = "bad request!";
    int expectedStatus = 400;

    // when
    CommonResponse<String> response = CommonResponse.fail(expectedStatus, expectedMessage);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatus()).isEqualTo(expectedStatus);
    assertThat(response.getResult()).isEqualTo(expectedMessage);
  }

  @Test
  @DisplayName("객체 생성 - fail")
  public void javaBean_fail() throws Exception {
    // given
    int expectedStatus = 400;

    // when
    CommonResponse<String> response = CommonResponse.fail(expectedStatus);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatus()).isEqualTo(expectedStatus);
    assertThat(response.getResult()).isEqualTo(null);
  }

  @Test
  @DisplayName("json 변환 test - success")
  public void serialize_success() throws Exception {
    // given
    CommonResponse<String> response = CommonResponse.success("test");
    String expected = "{\"status\":200,\"result\":\"test\"}";

    // when
    String result = objectMapper.writeValueAsString(response);

    // then
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("json 변환 test - fail with message")
  public void serialize_fail_message() throws Exception {
    // given
    String expectedMessage = "bad request!";
    int expectedStatus = 400;

    CommonResponse<String> response = CommonResponse.fail(expectedStatus, expectedMessage);
    String expected = "{\"status\":" + expectedStatus + ",\"result\":\"" + expectedMessage + "\"}";

    // when
    String result = objectMapper.writeValueAsString(response);

    // then
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("json 변환 test - fail")
  public void serialize_fail() throws Exception {
    // given
    int expectedStatus = 400;

    CommonResponse<String> response = CommonResponse.fail(expectedStatus);
    String expected = "{\"status\":" + expectedStatus + ",\"result\":null}";

    // when
    String result = objectMapper.writeValueAsString(response);

    // then
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }
}