package cotato.findweather.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemperatureDto {

    private double max;
    private double min;
}
