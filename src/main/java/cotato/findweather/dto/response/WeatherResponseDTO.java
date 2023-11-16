package cotato.findweather.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WeatherResponseDTO {

    String text;

    @Builder
    private WeatherResponseDTO(String text) {
        this.text = text;
    }

    public static WeatherResponseDTO from(String text) {
        return WeatherResponseDTO.builder()
                .text(text)
                .build();
    }
}
