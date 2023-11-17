package cotato.findweather.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class WeatherController {

    @Service
    public class WeatherService {

        public String test() {
            String stringMono = WebClient.create()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("apihub.kma.go.kr")
                            .path("/api/typ01/url/kma_sfcdd.php")
                            .queryParam("tm", "20231101")
                            .queryParam("stn", "0")
                            .queryParam("help", "1")
                            .queryParam("authKey", "PbPkF8c4Sraz5BfHOEq2Nw")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return stringMono;

        }

    }

    @GetMapping("/weather")
    public String getWeather() {
        WeatherService weatherService = new WeatherService();
        return weatherService.test();
    }
}
