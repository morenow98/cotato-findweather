package cotato.findweather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.util.Elements;
import javax.swing.text.Document;

@RestController
public class WeatherController {

    @GetMapping("/weather")
    public String getWeather() {

        // TODO : 날씨 정보 가져와 일교차 계산하기

        // << 최고기온 &obs=08 >>

        // 일별자료 API를 이용한 기상자료가 호출되지 않아 요소별자료의 url을 사용함
        String URL ="https://www.weather.go.kr/w/obs-climate/land/past-obs/obs-by-element.do?stn=108&yy=2023&obs=08";

        // 라이브러리 다운로드해서 lib에 넣었는데 Jsoup 인식 오류가 납니다 ㅜ.ㅜ
        Document doc = Jsoup.connect("https://www.weather.go.kr/w/obs-climate/land/past-obs/obs-by-element.do?stn=108&yy=2023&obs=08").get();
        System.out.println(doc);

        // << 최저기온 &obs=10 >>

        // 일별자료 API를 이용한 기상자료가 호출되지 않아 요소별자료의 url을 사용함
        String URL ="https://www.weather.go.kr/w/obs-climate/land/past-obs/obs-by-element.do?stn=108&yy=2023&obs=10";

        // 라이브러리 다운로드해서 lib에 넣었는데 Jsoup 인식 오류가 납니다 ㅜ.ㅜ
        Document doc = Jsoup.connect("https://www.weather.go.kr/w/obs-climate/land/past-obs/obs-by-element.do?stn=108&yy=2023&obs=10").get();
        System.out.println(doc);



        // 개발자 도구 켜서 크롤링을 하려고 보니.. 아래와 같이 data column명이 주어지고, row 값은 아무런 명명 없이 순서대로 나열
        // 되어 있어 어떻게 값을 가지고 와야할지 모르겠습니다..ㅎㅎ

        //<th scope="col" data-column="11" class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
        // role="columnheader aria-disabled="false" aria-controls="weather_table" unselectable="on" aria-sort="none"
        // aria-label="11월: No sort applied, activate to apply a descending sort" style="user-select: none;">
        // <div class="tablesorter-header-inner">11월</div></th>


        Elements elements = doc.select(".tablesorter-header tablesorter-headerUnSorted"); //??????
        for( Element element : elements) {
            System.out.println(element.text());
        }

        // 최고기온, 최저기온
        ta_max = ;
        tm_min = ;
        diff = ta_max - tm_min;

        return "최고 온도는 $ta_max 이고 최저 온도는 $tm_min 입니다. 일교차는 $diff 입니다.";
    }
}
