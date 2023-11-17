package cotato.findweather.controller;

import cotato.findweather.dto.TemperatureDto;
import cotato.findweather.service.WeatherService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public String getWeather() throws IOException {
        // TODO : 날씨 정보 가져와 일교차 계산하기
        TemperatureDto diff = weatherService.getDiff();
        StringBuilder sb = new StringBuilder();
        return sb.append("최고 온도는 ")
                .append(diff.getMax())
                .append(" 이고 최저 온도는 ")
                .append(diff.getMin())
                .append(" 입니다. 일교차는 ")
                .append(diff.getMax() - diff.getMin())
                .append("입니다.")
                .toString();
    }
}
