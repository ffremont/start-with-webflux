package com.example.demo.web.api;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static java.time.Duration.ofMinutes;

@RestController
@RequestMapping("/api/caching")
@Slf4j
public class CachingController {
    private final AsyncCache<String, String> cache = Caffeine.newBuilder().expireAfterWrite(ofMinutes(5)).maximumSize(1000).buildAsync();

    @GetMapping
    public Mono<String> basic(){
        return Mono.fromFuture(cache.get("basic", k -> {
            log.info("🧠 calcule de la chaine ;)");
            return "ceci est la valeur";
        }));
    }
}

