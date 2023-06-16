package com.example.demo.domain.port.spi;

import reactor.core.publisher.Mono;

public interface SecurityContextService {
     Mono<String> me();
}
