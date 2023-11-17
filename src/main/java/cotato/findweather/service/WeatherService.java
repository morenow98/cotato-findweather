package cotato.findweather.service;

import cotato.findweather.dto.WeatherDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeatherService {
    public WeatherDto getData() throws IOException {
        String url = "https://www.weather.go.kr/w/obs-climate/land/past-obs/obs-by-day.do";
        String yesterday = LocalDate.now()
                .minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] split = yesterday.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        String url2 = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("stn", 108)
                .queryParam("yy", year)
                .queryParam("mm", month)
                .toUriString();

        //System.out.println(url2);
        Document doc =Jsoup.connect(url2).get();
        Elements td = doc.select("td");
        int count=0;
        for(Element thisTd:td){
            String tdText = thisTd.text();

            if(tdText.contains("최고기온")){
                count++;
                if(count==day){
                    System.out.println(tdText);
                    tdText = tdText.replace("℃", ":");
                    String[] split2 = tdText.split(":");
                    double ta_max = Double.parseDouble(split2[3].trim());
                    double ta_min = Double.parseDouble(split2[5].trim());

                    return new WeatherDto(ta_max,ta_min);
                }
            }
        }
        return null;
    }
}
