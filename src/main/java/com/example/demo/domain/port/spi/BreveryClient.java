package com.example.demo.domain.port.spi;

import com.example.demo.domain.model.Brewery;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BreveryClient {
     Mono<List<Brewery>> findByState(String state);
}
