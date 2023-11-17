package cotato.findweather.service;

import cotato.findweather.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Service
public class WeatherService {

    @Value("${weather.api.auth_key}")
    private String authKey;

    public WeatherDto getWeather() throws IOException {
        LocalDate date = LocalDate.now().minusDays(1);
        String uri = UriComponentsBuilder
                .fromUriString("https://apihub.kma.go.kr")
                .path("api/typ01/url/kma_sfcdd3.php")
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
