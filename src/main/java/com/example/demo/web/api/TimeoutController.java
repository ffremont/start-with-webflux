package com.example.demo.web.api;

import com.example.demo.domain.model.Brewery;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@RestController
@RequestMapping("/api/timeout")
@TimeLimiter(name="api")
@Slf4j
public class TimeoutController {

    @GetMapping("/minute")
    public Mono<String> waitMinute() {
        return  Mono.delay(Duration.ofSeconds(60)).map(a -> "Après une minute !").doOnCancel(() -> {
            log.info("Cancel timeout");
        });
    }
}
