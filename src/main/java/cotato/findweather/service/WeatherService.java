package cotato.findweather.service;

import cotato.findweather.dto.TemperatureDto;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {

    public TemperatureDto getDiff() throws IOException {
        LocalDate yesterday = getYesterday();
        String uri = UriComponentsBuilder.fromUriString(
                        "https://www.weather.go.kr/w/obs-climate/land/past-obs/obs-by-day.do")
                .queryParam("stn", 108)
                .queryParam("yy", yesterday.getYear())
                .queryParam("mm", yesterday.getMonthValue())
                .toUriString();
        Connection connect = Jsoup.connect(uri);
        Document document = connect.get();

        int day = yesterday.getDayOfMonth();
        String targetDay = day + "일";
        Element target = document.getElementsContainingOwnText(targetDay).get(0);

        Element parents = target.parents().get(0);
        int index = parents.elementSiblingIndex();

        Element grandParent = parents.parent();
        assert grandParent != null;
        Elements next = grandParent.nextElementSiblings();
        Element element = next.get(0);
        Elements children = element.children();
        Element temperature = children.get(index);

        List<String> eachTemperature = List.of(temperature.text().split(" "));

        String max = eachTemperature.get(1).split(":")[1];
        String min = eachTemperature.get(2).split(":")[1];

        String maxValue = max.replace("℃", "");
        String minValue = min.replace("℃", "");

        return TemperatureDto.builder()
                .max(Double.parseDouble(maxValue))
                .min(Double.parseDouble(minValue))
                .build();
    }

    private LocalDate getYesterday() {
        return LocalDate.now().minusDays(1);
    }
}
