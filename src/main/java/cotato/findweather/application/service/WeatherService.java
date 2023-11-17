package cotato.findweather.application.service;

import cotato.findweather.application.port.in.DateQuery;
import cotato.findweather.application.port.out.GetWeatherPort;
import cotato.findweather.domain.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WeatherService {

    private final GetWeatherPort getWeatherPort;

    public Weather getWeatherData(DateQuery dateQuery) {
        // Assume some logic to retrieve weather data from the repository
        return getWeatherPort.getWeatherInfo(dateQuery.year(), dateQuery.month(), dateQuery.day());

    }

    public String getFormattedWeatherInfo(DateQuery dateQuery) {
        Weather weather = getWeatherData(dateQuery.previousDay());

        // Format the information using the Weather class method
        return weather.formatWeatherInfo();
    }

}
