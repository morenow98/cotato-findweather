package cotato.findweather.service;

import cotato.findweather.dto.response.WeatherResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.auth-key}")
    private String authKey;


    public WeatherResponseDTO getWeather() {

        LocalDate now = LocalDate.now().minusDays(1);

        String uri = "https://apihub.kma.go.kr/api/typ01/url/kma_sfcdd.php?tm="
                + now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "&stn=108&authKey="
                + authKey;

        String result = WebClient.create()
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Float maxTemp = Float.valueOf(result.split(",")[11]);
        Float minTemp = Float.valueOf(result.split(",")[13]);

        String text = String.format("최고 온도는 %.1f 이고 최저 온도는 %.1f 입니다. 일교차는 %.1f 입니다.", maxTemp, minTemp, (maxTemp - minTemp));

        return WeatherResponseDTO.from(text);
    }

}
