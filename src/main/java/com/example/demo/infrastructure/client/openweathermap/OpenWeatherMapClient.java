package com.example.demo.infrastructure.client.openweathermap;

import com.example.demo.domain.model.Weather;
import com.example.demo.domain.port.spi.WeatherClient;
import com.example.demo.infrastructure.client.openweathermap.model.WeatherResponse;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
public class OpenWeatherMapClient implements WeatherClient {

    private final Environment environment;


    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openweathermap.org/data/2.5")
            .build();

    public OpenWeatherMapClient(Environment environment) {
        this.environment = environment;
    }

    public Mono<Weather> whatTheWeatherLike(String lat, String lon){
        return webClient.get()
                .uri(uri -> uri.path("/weather")
                        .queryParam("lat", lat)
                        .queryParam("lon", lat)
                        .queryParam("appid", environment.getProperty("OWM_KEY"))
                        .build())
                .retrieve()

                .bodyToMono(WeatherResponse.class)
                .flatMap(wp -> wp.weather().stream().findFirst().map(Mono::just).orElse(Mono.empty()))
                .delayElement(Duration.ofSeconds(3))
                .timeout(Duration.ofSeconds(1))
                ;
    }
}
