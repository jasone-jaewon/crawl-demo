package com.hyundai.crawldemo.domain.alphanumeric.impl;

import static com.hyundai.crawldemo.domain.util.StringUtil.EMPTY_STRING;

import com.hyundai.crawldemo.domain.alphanumeric.model.Alphanumeric;
import com.hyundai.crawldemo.domain.alphanumeric.port.AlphanumericService;
import com.hyundai.crawldemo.domain.util.StringUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlphanumericServiceImpl implements AlphanumericService {

  /**
   * 중복 제거된 알파벳과 숫자 목록 객체 파싱
   *
   * @param inputs input
   * @return 대문자, 소문자, 숫자 객체
   */
  @Override
  public Alphanumeric parse(List<String> inputs) {
    String joinedInput = String.join(EMPTY_STRING, inputs);
    String notDuplicatedInput = StringUtil.removeDuplicate(joinedInput);
    String alphanumericInput = StringUtil.extractAlphanumeric(notDuplicatedInput);

    Alphanumeric alphanumeric = Alphanumeric.createByString(alphanumericInput);
    alphanumeric.sort();

    return alphanumeric;
  }
}
