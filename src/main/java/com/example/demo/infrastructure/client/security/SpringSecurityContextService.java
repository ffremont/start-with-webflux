package com.example.demo.infrastructure.client.security;

import com.example.demo.domain.port.spi.SecurityContextService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SpringSecurityContextService implements SecurityContextService {

    public Mono<String> me(){
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(principal -> ((User)principal).getUsername());
    }
}
