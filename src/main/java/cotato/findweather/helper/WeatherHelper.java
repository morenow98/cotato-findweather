package cotato.findweather.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class WeatherHelper {

    @Value("${weather.auth-key}")
    private String authKey;

    public String getResult(LocalDate now) {

        String uri = "https://apihub.kma.go.kr/api/typ01/url/kma_sfcdd.php?tm="
                + now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "&stn=108&authKey="
                + authKey;

        return WebClient.create()
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getText(String result) {
        Float maxTemp = Float.valueOf(result.split(",")[11]);
        Float minTemp = Float.valueOf(result.split(",")[13]);

        return String.format("최고 온도는 %.1f 이고 최저 온도는 %.1f 입니다. 일교차는 %.1f 입니다.", maxTemp, minTemp, (maxTemp - minTemp));
    }
}
