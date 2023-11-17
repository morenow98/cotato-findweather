package cotato.findweather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cotato.findweather.dto.WeatherDto;
import cotato.findweather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;
    @GetMapping("/weather")
    public String getWeather() throws IOException {
        // TODO : 날씨 정보 가져와 일교차 계산하기

    WeatherDto weatherDto = weatherService.getWeatherInfo();
    double diff = weatherDto.getTa_max() - weatherDto.getTm_min();
    String answer = String.format("최고 온도는 %.1f도이고 최저 온도는 %.1f도입니다. 일교차는 %.2f도입니다.", weatherDto.getTa_max(), weatherDto.getTm_min(), diff );
    return answer;
    }
}
