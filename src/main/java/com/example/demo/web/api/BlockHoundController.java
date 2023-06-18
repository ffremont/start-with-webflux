package com.example.demo.web.api;

import com.example.demo.domain.model.Brewery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.xml.bind.JAXB;
import java.util.UUID;

@RestController
@RequestMapping("/api/blockhound")
public class BlockHoundController {
    @GetMapping("/ok")
    public Mono<Brewery> getBlocking() {
        return  Mono.fromCallable(() -> {
            Thread.sleep(100);
            return new Brewery("bidon", "niort", "79000", "0", "0");
        }).publishOn(Schedulers.boundedElastic());
    }

    @GetMapping("/ko")
    public Mono<Brewery> getBlockingKO() {
        return Mono.fromCallable(() -> {
            Thread.sleep(100);
            return new Brewery("bidon", "niort", "79000", "0", "0");
        });
    }


    @GetMapping("/ko/xml")
    public Mono<Brewery> getBlockingXml() {
        return Mono.fromCallable(() -> {
            return JAXB.unmarshal("<Brewery><name>Bison</name></Brewery>", Brewery.class);
        });
    }


    @GetMapping("/ko/uuid")
    public Mono<Brewery> getBlockingUUIDKO() {
        return Mono.fromCallable(() ->
             new Brewery(UUID.randomUUID().toString(), "niort", "79000", "0", "0"));
    }
}
