package cotato.findweather.service;

import cotato.findweather.dto.WeatherDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WebClientService {

    @Value("${weather.api.auth_key}")
    private String authKey;

    public WeatherDto getWeather() {
        LocalDate date = LocalDate.now().minusDays(1);
        String uri = UriComponentsBuilder
                .fromUriString("https://apihub.kma.go.kr")
                .path("/api/typ01/url/kma_sfcdd.php")
                .queryParam("tm", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .queryParam("stn", 108)
                .queryParam("authKey", authKey)
                .toUriString();
        String result = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String[] info = result.split("\n")[5].split(",");
        double highTemp = Double.parseDouble(info[11]);
        double lowTemp = Double.parseDouble(info[13]);
        return new WeatherDto(highTemp, lowTemp);
    }
}

