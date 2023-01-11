package com.hyundai.crawldemo.adapter.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.crawldemo.application.cache.port.CacheService;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Slf4j
@Service
public class RedisService implements CacheService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapper objectMapper;

  /**
   * redis value 조회
   *
   * @param key       조회할 key
   * @param classType 조회 대상 class
   * @return 조회 value
   */
  @Override
  public <T> Optional<T> getValue(String key, Class<T> classType) {
    String value = (String) redisTemplate.opsForValue().get(key);
    if (!StringUtils.hasText(value)) {
      return Optional.empty();
    }
    try {
      return Optional.of(objectMapper.readValue(value, classType));
    } catch (JsonProcessingException e) {
      log.warn("redis 조회 실패. deserialize 실패. key: " + key + ", value: " + value);
      return Optional.empty();
    }
  }

  /**
   * redis value 등록 - default 만료시간 1초
   *
   * @param key   등록할 key
   * @param value 등록 대상
   */
  @Override
  public void setValue(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
      redisTemplate.expire(key, 1, TimeUnit.MINUTES);
    } catch (JsonProcessingException e) {
      log.warn("redis 객체 등록 실패. serialize 실패. key: " + key + ", value: " + value);
    }
  }
}
