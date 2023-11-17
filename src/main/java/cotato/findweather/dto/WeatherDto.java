package cotato.findweather.dto;

public class WeatherDto {
    private double ta_max;
    private double tm_min;

    public WeatherDto(double ta_max, double tm_min) {
        this.ta_max = ta_max;
        this.tm_min = tm_min;
    }

    public double getTa_max() {
        return ta_max;
    }

    public double getTm_min() {
        return tm_min;
    }
}
