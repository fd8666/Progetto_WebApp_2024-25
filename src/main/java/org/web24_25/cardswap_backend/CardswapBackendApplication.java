package org.web24_25.cardswap_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CardswapBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardswapBackendApplication.class, args);
    }
}