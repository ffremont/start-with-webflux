package com.example.demo.domain.port.spi;

import com.example.demo.domain.model.Weather;
import reactor.core.publisher.Mono;

public interface WeatherClient {
    Mono<Weather> whatTheWeatherLike(String lat, String lon);
}
