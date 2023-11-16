package cotato.findweather.adapter.out.api;

import cotato.findweather.application.port.out.GetWeatherPort;
import cotato.findweather.domain.Weather;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
class GetWeatherJsoup implements GetWeatherPort {

    private final String BASE_URL = "https://www.weather.go.kr/w/obs-climate/land/past-obs/obs-by-day.do";

    @Override
    public Weather getWeatherInfo(int year, int month, int day) {
        Document doc = null;

        try {
            String url = BASE_URL + "?stn=108&yy=" + year + "&mm=" + month;
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element table = doc.select("table.table-col.table-cal > tbody").first();
        boolean found = false;

        int column = 0;
        int row = 0;
        if (table != null) {
            Elements rows = table.select("tr");

            for (int i = 0; i < rows.size(); i += 2) {
                Element dateRow = rows.get(i);
                Elements dateColumn = dateRow.select("td span");

                for (int j = 0; j < dateColumn.size(); j++) {
                    if (dateColumn.get(j).text().equals(day + "일")) {
                        column = j;
                        row = i + 1;
                        found = true;
                        break;  // Exit the inner loop
                    }
                }
                if (found) {
                    break;  // Exit the outer loop
                }
            }
        }

        if (found) {
            Elements data = table.select("tr").get(row).select("td").get(column).select("span");

            int index = 0; // Index to keep track of the data element being processed

// Variables to store non-empty values
            Double averageTemperature = null;
            Double maximumTemperature = null;
            Double minimumTemperature = null;
            Double averageCloudCover = null;
            Double dailyPrecipitation = null;

            for (Element value : data) {
                String text = value.text();

                // Skip processing if the text is empty
                if (text.isEmpty()) {
                    continue;
                }

                // Use the index to determine which property to set in the Weather class
                switch (index) {
                    case 0:
                        averageTemperature = getTemperatureValue(text);
                        break;
                    case 1:
                        maximumTemperature = getTemperatureValue(text);
                        break;
                    case 2:
                        minimumTemperature = getTemperatureValue(text);
                        break;
                    case 3:
                        averageCloudCover = getCloudCoverValue(text);
                        break;
                    case 4:
                        dailyPrecipitation = getPrecipitationValue(text);
                        break;
                    // Handle additional cases if needed
                }

                // Increment the index for the next iteration
                index++;
            }

            return new Weather(averageTemperature, maximumTemperature,
                    minimumTemperature, averageCloudCover,dailyPrecipitation);

        }

        return null;
    }

    private static Double getTemperatureValue(String text) {
        // Extract the temperature value from the text using a regular expression
        // Adjust the regular expression based on the actual format of your data
        String pattern = "(-?\\d+\\.?\\d*)℃";
        return extractDoubleValue(text, pattern);
    }

    private static Double getCloudCoverValue(String text) {
        // Extract the cloud cover value from the text using a regular expression
        // Adjust the regular expression based on the actual format of your data
        String pattern = "(-?\\d+\\.?\\d*)";
        return extractDoubleValue(text, pattern);
    }

    private static Double getPrecipitationValue(String text) {
        // Extract the precipitation value from the text using a regular expression
        // Adjust the regular expression based on the actual format of your data
        String pattern = "(-?\\d+\\.?\\d*)";
        return extractDoubleValue(text, pattern);
    }

    private static Double extractDoubleValue(String text, String pattern) {
        // Use a regular expression to extract the double value from the text
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(text);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return null;
    }
}
