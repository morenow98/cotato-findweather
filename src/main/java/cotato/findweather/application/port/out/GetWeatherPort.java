package cotato.findweather.application.port.out;

import cotato.findweather.domain.Weather;

public interface GetWeatherPort {

    Weather getWeatherInfo(int year, int month, int day);

}
