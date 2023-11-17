package cotato.findweather.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class WeatherController {

    private final WebClient webClient;

    public WeatherController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://apihub.kma.go.kr/api/typ01/url/kma_sfcdd3.php?tm1=20151211&tm2=20151214&stn=108&help=1&authKey=DU__UBaFSAGP_1AWhVgBtA").build();
    }

    @GetMapping("/weather")
    public Mono<String> getWeather() {
        // TODO : 날씨 정보 가져와 일교차 계산하기
        // API로부터 날씨 정보 가져오기
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("tm", "20231101")
                        .queryParam("stn", 108)
                        .queryParam("authKey", "DU__UBaFSAGP_1AWhVgBtA")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    // TODO: 날씨 정보에서 최고 온도와 최저 온도 추출 및 일교차 계산
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode = objectMapper.readTree(response);

                        //최고 온도와 최저 온도 추출
                        double taMax = rootNode.path("main").path("TA_MAX").asDouble();
                        double tmMin = rootNode.path("main").path("TA_MIN").asDouble();

                        //일교차 계산
                        double diff = taMax - tmMin;

                        return String.format("최고 온도는 %.1f 이고 최저 온도는 %.1f 입니다. 일교차는 %.1f 입니다.", taMax, tmMin, diff);
                    }
                    catch (IOException e) {
                        //예외 처리
                        e.printStackTrace();
                        return "날씨 정보를 가져오는 중에 오류가 발생했습니다.";
                    }
                });
    }
}