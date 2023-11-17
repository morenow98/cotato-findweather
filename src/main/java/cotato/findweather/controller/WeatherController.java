package cotato.findweather.controller;

import cotato.findweather.dto.WeatherDto;
import cotato.findweather.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WeatherController {

    @GetMapping("/weather")
    public String getWeather() throws IOException {
        // TODO : 날씨 정보 가져와 일교차 계산하기
        WeatherService weatherService = new WeatherService();
        WeatherDto data = weatherService.getData();
        if (data==null) {
            return "오류가 발생했습니다";
        }
        double result3 =  data.getTa_max()-data.getTm_min();
        double result = Math.round(result3*100) / 100.0;
        return "최고 온도는"+data.getTa_max()+"이고 최저 온도는"+data.getTm_min()+"입니다. 일교차는" + result+"입니다.";
    }
}
