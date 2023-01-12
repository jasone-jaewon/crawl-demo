package com.hyundai.crawldemo.application.cache.port;

import java.util.Optional;

public interface CacheService {

  /**
   * redis value 조회
   *
   * @param key       조회할 key
   * @param classType 조회 대상 class
   * @return 조회 value
   */
  <T> Optional<T> getValue(String key, Class<T> classType);

  /**
   * redis value 등록 - default 만료시간 1초
   *
   * @param key   등록할 key
   * @param value 등록 대상
   */
  void setValue(String key, Object value);
}
