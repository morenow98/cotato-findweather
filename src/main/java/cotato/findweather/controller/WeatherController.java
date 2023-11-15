package cotato.findweather.controller;

import cotato.findweather.dto.WeatherDto;
import cotato.findweather.service.JsoupService;
import cotato.findweather.service.WebClientService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private JsoupService jsoupService;

    @Autowired
    private WebClientService webClientService;

    @GetMapping("/weather")
    public String getJsoupWeather() throws IOException {

        String answer;

        WeatherDto weatherDto = jsoupService.getWeather();
        answer = "Jsoup 사용 : "
                + "최고 온도는 " + weatherDto.getHighTemp()
                + " 이고 최저 온도는 " + weatherDto.getLowTemp()
                + " 입니다. 일교차는 " + (weatherDto.getHighTemp() - weatherDto.getLowTemp())
                + " 입니다."
                + "\n\n";

        weatherDto = webClientService.getWeather();
        answer += "WebClient 사용 : "
                + "최고 온도는 " + weatherDto.getHighTemp()
                + " 이고 최저 온도는 " + weatherDto.getLowTemp()
                + " 입니다. 일교차는 " + (weatherDto.getHighTemp() - weatherDto.getLowTemp())
                + " 입니다."
                + "\n\n";

        return answer;
    }

}
