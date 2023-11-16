package cotato.findweather.controller;

import cotato.findweather.dto.response.WeatherResponseDTO;
import cotato.findweather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public WeatherResponseDTO getWeather() {
        // TODO : 날씨 정보 가져와 일교차 계산하기
        return weatherService.getWeather();
    }
}
