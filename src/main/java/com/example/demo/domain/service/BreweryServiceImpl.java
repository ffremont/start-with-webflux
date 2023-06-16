package com.example.demo.domain.service;

import com.example.demo.domain.model.Trend;
import com.example.demo.domain.port.api.BreweryService;
import com.example.demo.domain.port.spi.BreveryClient;
import com.example.demo.domain.port.spi.SecurityContextService;
import com.example.demo.domain.port.spi.WeatherClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BreweryServiceImpl implements BreweryService {

    private final BreveryClient breweryClient;

    private final WeatherClient weatherClient;

    private final SecurityContextService securityContextService;

    public BreweryServiceImpl(BreveryClient breweryClient, WeatherClient weatherClient, SecurityContextService securityContextService) {
        this.breweryClient = breweryClient;
        this.weatherClient = weatherClient;
        this.securityContextService = securityContextService;
    }

    public Mono<List<Trend>> trendsOfFrenchBreweries() {
        return Mono.zip(
                        securityContextService.me(),
                        breweryClient.findByState("France")
                )
                .flatMap(tuple ->
                        Flux.fromIterable(tuple.getT2())
                                .flatMap(brewery ->
                                                weatherClient.whatTheWeatherLike(brewery.latitude(), brewery.longitude())
                                                        .map(weather -> new Trend(brewery, weather, "cloud".equalsIgnoreCase(weather.main()), tuple.getT1()))
                                        , 3)

                                .collectList()
                );
    }
}
