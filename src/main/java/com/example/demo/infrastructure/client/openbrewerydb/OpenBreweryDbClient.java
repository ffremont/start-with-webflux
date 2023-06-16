package com.example.demo.infrastructure.client.openbrewerydb;

import com.example.demo.domain.port.spi.BreveryClient;
import com.example.demo.domain.model.Brewery;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class OpenBreweryDbClient implements BreveryClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openbrewerydb.org/v1")
            .build();

    public Mono<List<Brewery>> findByState(String state){
        return webClient.get()
                .uri(uri -> uri.path("/breweries")
                        .queryParam("by_country", state)
                        .build())
                .retrieve()

                .bodyToMono(new ParameterizedTypeReference<>(){})
                ;
    }

}
