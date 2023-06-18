package com.example.demo.web.api;

import com.example.demo.domain.model.Brewery;
import com.example.demo.domain.model.Trend;
import com.example.demo.domain.port.api.BreweryService;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController()
@RequestMapping("/api/breweries")
public class BreweryController {

    private final BreweryService breweryService;

    public BreweryController(BreweryService breweryService) {
        this.breweryService = breweryService;
    }

    @GetMapping()
    public Mono<List<Trend>> findAll(){
        return breweryService.trendsOfFrenchBreweries();
    }
}
