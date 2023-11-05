# 코테이토 네트워킹 11/17

---

# 날씨 정보 가져오기

- 코테이토 감자 분들은 다음 미션을 11/17일까지 수행해주시면 됩니다!
- 이번 네트워킹 미션은 객체 지향적인 좋은 코드를 목표로 하는 것이 아니라, Jsoup 혹은 Web Client 를 경험해 보는 것이 목표입니다.
- 1팀은 Jsoup 을, 2팀은 Web Client 를 이용하면 됩니다. 단, 다른 팀의 미션을 원하거나 두 미션을 모두 경험하고 싶으면 원하는 대로 하셔도 됩니다.
- build.gradle 에 작성된 버전을 기준으로 합니다. Spring Boot 는 3.1.5, Java 는 17 버전을 활용합니다.

---

## 1단계 : Fork & Clone
- 현재 Repository를 각자 Fork한 후 Clone한 뒤 작업환경을 만든다.
- 새로운 branch 를 만들어 해당 branch 에서 작업한다.
- 의존성 추가는 자유롭게 해도 된다.

## 2단계 : 날씨 정보 가져와 일교차 구하기
- `localhost:8080/weather` 에 접속하면 (Get 요청하면) 요청한 날의 전 날, 즉 어제 날짜의 서울 날씨를 가져와 일교차를 계산 후 다음의 문자열을 return 한다. <br>
  ```최고 온도는 $ta_max 이고 최저 온도는 $tm_min 입니다. 일교차는 $diff 입니다.```
- 단, 어제 날짜가 어렵다고 생각된다면 2023년 11월 1일의 날씨를 고정적으로 가져와도 된다.
- 가져오는 방식은 Jsoup(1팀) 혹은 Web Client(2팀)를 이용한다.

### Jsoup 으로 날씨 정보 가져오기
- 다음 게시글을 참고하거나 직접 검색하여 Jsoup 설정을 한다. <br>
https://vmpo.tistory.com/113
- 다음 주소의 기상청 날씨누리 사이트를 크롤링해 파싱하여 기온값을 알아낸다. <br>
https://www.weather.go.kr/w/obs-climate/land/past-obs/obs-by-day.do?stn=108&yy=0000&mm=00 <br>
여기서 stn 은 서울의 stn 값인 108을 고정 사용한다. yy와 mm에는 요청할 날짜의 년,월을 입력한다.

### Web Client 로 날씨 정보 가져오기
- 다음 게시글을 참고하거나 직접 검색하여 Web Client 설정을 한다. <br>
https://gngsn.tistory.com/154
- 기상청 API 허브 사이트의 지상관층 -> 종간기상관측(ASOS) API를 활용한다. 이 중, 특정일의 날씨를 조회하는 `일자료`를 사용한다. `시간자료`를 사용하지 않는다.
- 기상청 API 허브 사이트에 회원가입 후 인증키를 발급받아 API 호출에 활용한다.
- 발급받은 인증키는 절대 Github에 push 되지 않도록 한다. .gitignore 에 등록된 properties 혹은 yml 등의 설정파일에 인증키를 넣어놓고, @Value 어노테이션을 활용한다.

## 3단계 : Pull Request
- 구현이 완료되었다면 commit 후 push 한다.
- 자신의 github repository 에서 새로운 pull request 를 만들어 보낸다.