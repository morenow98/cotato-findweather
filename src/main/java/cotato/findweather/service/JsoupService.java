package cotato.findweather.service;

import cotato.findweather.dto.WeatherDto;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class JsoupService {

    public WeatherDto getWeather() throws IOException {
        String yesterday = LocalDate.now()
                .minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int year = Integer.parseInt(yesterday.split("-")[0]);
        int month = Integer.parseInt(yesterday.split("-")[1]);
        int date = Integer.parseInt(yesterday.split("-")[2]);
        String uri = UriComponentsBuilder
                .fromUriString("https://www.weather.go.kr/w/obs-climate/land/past-obs/obs-by-day.do")
                .queryParam("stn", 108)
                .queryParam("yy", year)
                .queryParam("mm", month)
                .toUriString();
        Connection connect = Jsoup.connect(uri);
        Document document = connect.get();
        Element dateElement = document.getElementsContainingOwnText(date + "일").get(0);
        Element dateElementParent = dateElement.parent();
        Element dateElementGrandParent = dateElementParent.parent();
        int index = -1;
        for (int i = 0; i < dateElementGrandParent.childrenSize(); i++) {
            if (dateElementGrandParent.children().get(i).equals(dateElementParent)) {
                index = i;
                break;
            }
        }
        Element infoElementGrandParent = dateElementGrandParent.nextElementSibling();
        String info = infoElementGrandParent.children().get(index).text();
        String highTempString = info.split(" ")[1];
        String lowTempString = info.split(" ")[2];
        double highTemp = Double.parseDouble(highTempString.replaceAll("[^0-9.]", ""));
        double lowTemp = Double.parseDouble(lowTempString.replaceAll("[^0-9.]", ""));
        return new WeatherDto(highTemp, lowTemp);
    }
}
