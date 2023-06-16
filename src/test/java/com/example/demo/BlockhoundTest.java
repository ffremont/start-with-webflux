package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockhoundTest {

    @Test
    void sample() {
        final Function<byte[], String> bytesToHex = (hash) -> {
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        };

        StepVerifier.create(Mono.fromSupplier(() -> {
            var is = Thread.currentThread().getContextClassLoader().getResourceAsStream("hello.txt");
            String content = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
                Thread.sleep(1000);
                byte[] encodedhash = digest.digest(
                        content.getBytes(StandardCharsets.UTF_8));
                return bytesToHex.apply(encodedhash);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        })
                .doOnNext(it -> {
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .log()).assertNext(res -> {
            assertThat(res).isEqualTo("80d37da561b9702350ba46ecc56cbb85d628bd27990d72fbdcd605b1198881ed");
        });
    }
}
