package cotato.findweather.dto;

public class WeatherDto {

    private final double highTemp;
    private final double lowTemp;

    public WeatherDto(double highTemp, double lowTemp) {
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }

    public double getHighTemp() {
        return highTemp;
    }

    public double getLowTemp() {
        return lowTemp;
    }
}
