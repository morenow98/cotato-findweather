package cotato.findweather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @GetMapping("/weather")
    public String getWeather() {
        // TODO : 날씨 정보 가져와 일교차 계산하기
        return "최고 온도는 $ta_max 이고 최저 온도는 $tm_min 입니다. 일교차는 $diff 입니다.";
    }
}
