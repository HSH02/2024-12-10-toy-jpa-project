package com.jpa.toyjpaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ToyJpaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToyJpaProjectApplication.class, args);
    }

}
