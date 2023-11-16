package cotato.findweather.service;

import cotato.findweather.dto.response.WeatherResponseDTO;
import cotato.findweather.helper.WeatherHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherHelper weatherHelper;

    public WeatherResponseDTO getWeather() {

        LocalDate now = LocalDate.now().minusDays(1);

        String result = weatherHelper.getResult(now);
        String text = weatherHelper.getText(result);

        return WeatherResponseDTO.from(text);
    }
}
