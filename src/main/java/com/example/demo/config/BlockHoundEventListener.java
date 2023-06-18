package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.blockhound.BlockHound;

@Profile("blockhound")
@Component
@Slf4j
public class BlockHoundEventListener  {
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        BlockHound.install();
        log.info("🛡️ Blockhound est chargé !");
    }
}
