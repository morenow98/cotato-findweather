package cotato.findweather.controller;


import cotato.findweather.dto.WeatherDto;
import cotato.findweather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public String getWeather () throws IOException {

        String answer;

        WeatherDto weatherDto = weatherService.getWeather();
        answer = "WebClient 사용 : "
                + "최고 온도는 " + weatherDto.getHighTemp()
                + " 이고 최저 온도는 " + weatherDto.getLowTemp()
                + " 입니다. 일교차는 " + (weatherDto.getHighTemp() - weatherDto.getLowTemp())
                + " 입니다."
                + "\n\n";

        return answer;
    }

}
