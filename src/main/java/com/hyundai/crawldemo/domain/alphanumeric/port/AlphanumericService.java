package com.hyundai.crawldemo.domain.alphanumeric.port;

import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import java.util.List;

public interface AlphanumericService {

  /**
   * 중복 제거된 알파벳과 숫자 목록 객체 파싱
   *
   * @param inputs input
   * @return 대문자, 소문자, 숫자 객체
   */

  Alphanumeric parse(List<String> inputs);

}
