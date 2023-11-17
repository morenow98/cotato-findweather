package cotato.findweather.adapter.in.web;

import cotato.findweather.application.port.in.DateQuery;
import cotato.findweather.application.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public String getWeather(@RequestParam int year, @RequestParam int month, @RequestParam int day) {
        DateQuery dateQuery = new DateQuery(year, month, day);
        return weatherService.getFormattedWeatherInfo(dateQuery);
    }
}
