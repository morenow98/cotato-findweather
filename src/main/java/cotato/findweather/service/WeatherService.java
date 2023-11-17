package cotato.findweather.service;

import cotato.findweather.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class WeatherService {

    @Value("${weather-api-Key}")
    private String authKey;

    public WeatherDto getWeatherInfo() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, -1);
        String yesterday = sdf.format(c1.getTime());
//        System.out.println(yesterday);
        String uri = UriComponentsBuilder
                .fromUriString("https://apihub.kma.go.kr")
                .path("/api/typ01/url/kma_sfcdd.php")
                .queryParam("tm", yesterday)
                .queryParam("stn", 108)
                .queryParam("authKey", authKey)
                .toUriString();
//        WebClient webClient = WebClient.create(WEATHER_API_URL);

        String response = WebClient.create().get().uri(uri).retrieve().bodyToMono(String.class).block();
//        System.out.println(response);

        InputStream is = new ByteArrayInputStream(response.getBytes());

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        String[] weatherInfo = null;
        int i = 0;
        while((line = br.readLine()) != null){
            if (line.charAt(0) != '#'){
                weatherInfo = line.split(",");
            }
        }
        double ta_max = Double.parseDouble(weatherInfo[11]);
        double tm_min = Double.parseDouble(weatherInfo[13]);
        return new WeatherDto(ta_max,tm_min);
    }
}
