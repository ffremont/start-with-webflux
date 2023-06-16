package com.example.demo.domain.port.api;

import com.example.demo.domain.model.Trend;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BreweryService {
     Mono<List<Trend>> trendsOfFrenchBreweries();
}
