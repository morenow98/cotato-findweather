package cotato.findweather.adapter.out.api;

import cotato.findweather.application.port.out.GetWeatherPort;
import cotato.findweather.domain.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
//@Repository
public class GetWeatherApi implements GetWeatherPort {

    @Value("${api.auth-key}")
    private String key;

    private static final String BASE_URL = "https://apihub.kma.go.kr/api/typ01/url/kma_sfcdd.php?stn=108";

    @Override
    public Weather getWeatherInfo(int year, int month, int day) {
        WebClient webClient = WebClient.create();

        String dateString = LocalDate.of(year, month, day).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Construct the full URL with query parameters
        String url = String.format("%s&tm=%s&authKey=%s", BASE_URL, dateString, key);
        log.info("url = {}", url);

        // Make the GET request and retrieve the response
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseWeatherResponse)
                .block();
    }

    // Method to parse the weather response and extract TMMAX and TMMIN values
    private Weather parseWeatherResponse(String response) {
        String[] lines = response.split("\n");
        if (lines.length >= 3) {
            String dataLine = lines[5]; // Assuming TMMAX and TMMIN values are in the third line
            String[] values = dataLine.split(",");
            if (values.length >= 15) { // Ensure there are at least 15 values
                // Extract TA_AVG, TA_MAX, and TA_MIN values and populate the Weather object
                double taAvg = Double.parseDouble(values[10]);
                double taMax = Double.parseDouble(values[11]);
                double taMin = Double.parseDouble(values[13]);

                log.info("평균 = {}", taAvg);
                log.info("최고 = {}", taMax);
                log.info("최저 = {}", taMin);
                return new Weather(taAvg, taMax, taMin, null, null); // Assuming your Weather class constructor
            }
        }

        // Return a default Weather object or handle the case when values are not found
        return null;
    }

}
