package com.hyundai.crawldemo.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResponse<T> {

  @Schema(description = "상태값")
  private final int status;

  @Schema(description = "결과")
  private final T result;

  public static <T> CommonResponse<T> fail(int status) {
    return new CommonResponse<>(status, null);
  }

  public static CommonResponse<String> fail(int status, String message) {
    return new CommonResponse<>(status, message);
  }

  public static <T> CommonResponse<T> success(T result) {
    int status = HttpStatus.OK.value();
    return new CommonResponse<>(status, result);
  }
}
