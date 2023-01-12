# crawl-demo
## 설치 필요
* docker
### docker-compose
* 캐싱을 위해 redis가 필요합니다.
```yml
version: '3.7'

services:
  hyundai-redis:
    image: redis
    ports:
      - 6379:6379
    volumes:
      - ./data:/var/lib/redis
```
```shell
# 실행
$ docker-compose up -d

# 종료
$ docker-compose down -v
```
## library
* redis
    * 캐싱을 위해 사용
* jsoup
    * 크롤링을 위해 사용
* springdoc
    * api 문서 자동화를 위해 적용
* lombok

## API 명세서
* 서버 실행 후, `http://localhost:8080/swagger-ui/index.html#` 접속하시면 됩니다.
## 설계 방향
### 패키지 structure
* 헥사고날 아키텍처를 적용하였습니다.
    * 비즈니스 로직이 외부요소에 의존하지 않게 고려하여 구성하였습니다.
    * 각 계층이 최대한 분리되게 하였습니다.
    * 구현체를 바로 참조하는 것이 아닌 port interface 를 참조하도록 하여 구현체가 변경되어도 사용처의 코드 수정이 없도록 설계하였습니다.
* presentation level -> application level -> domain level -> infrastructure level
* adapter 패키지를 구성하여 다른 계층들이 특정 라이브러리에 의존하지 않도록 설계하였습니다.
  * 예를 들어 캐시를 redis에서 다른 라이브러리로 변경시, adapter 하위 패키지만 변경하면 가능하게 설계하였습니다.

## 구현 과정
- 개발일지 작성
  - 1일차(10일)
    - 기능 명세서 분석
    - domain 로직 구현
      - 크롤링 dependency 조사
      - 크롤링 로직 추가
      - 알파벳,숫자 추출 작업 및 문자열 중복 제거 로직 추가
    - 2일차(11일)
      - 크롤링 api presentation level 구현 
      - exception 처리 구현
      - 전체적인 refactoring
        - 크롤링 결과값을 객체로 변경
        - application level에서 처리결과를 조합하도록 변경
      - 캐싱 redis 적용
    - 3일차(12일)
      - swagger 적용
      - sorting refactoring