package com.example.demo.domain.service;

import com.example.demo.domain.model.Brewery;
import com.example.demo.domain.model.Weather;
import com.example.demo.domain.port.spi.BreveryClient;
import com.example.demo.domain.port.spi.SecurityContextService;
import com.example.demo.domain.port.spi.WeatherClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BreweryServiceImplTest {

    @InjectMocks
    private BreweryServiceImpl breweryService;

    @Mock
    private  BreveryClient breweryClient;

    @Mock
    private  WeatherClient weatherClient;

    @Mock
    private  SecurityContextService securityContextService;


    @Nested
    @DisplayName("trendsOfFrenchBreweries(...)")
    class TrendsOfFrenchBreweries{
        @Test
        @DisplayName("Vérifie que l'enchainement se déroule bien")
        void enchainement(){
            var vNb = new Brewery("VnB", "niort", "79000", "0","0");
            var niort = new Weather(800, "Clear", "très beau", "");
            when(breweryClient.findByState(anyString())).thenReturn(Mono.just(List.of(vNb)));
            when(weatherClient.whatTheWeatherLike(anyString(), anyString())).thenReturn(Mono.just(niort));
            when(securityContextService.me()).thenReturn(Mono.just("florent"));

            StepVerifier.create(breweryService.trendsOfFrenchBreweries())
                    .assertNext(result ->{
                        assertThat(result).hasSize(1);
                    })
                    .verifyComplete();
        }
    }



}