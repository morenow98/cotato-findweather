package cotato.findweather.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;


@ToString
@AllArgsConstructor
public class Weather {

    private Double averageTemperature;
    private Double maximumTemperature;
    private Double minimumTemperature;
    private Double averageCloudCover;
    private Double dailyPrecipitation;

    // Method to format the information
    public String formatWeatherInfo() {
        String template = "최고 온도는 $ta_max 이고 최저 온도는 $tm_min 입니다. 일교차는 $diff 입니다.";

        // Replace placeholders with actual values

        return template
                .replace("$ta_max", String.valueOf(maximumTemperature))
                .replace("$tm_min", String.valueOf(minimumTemperature))
                .replace("$diff", String.valueOf(maximumTemperature - minimumTemperature));
    }
}
