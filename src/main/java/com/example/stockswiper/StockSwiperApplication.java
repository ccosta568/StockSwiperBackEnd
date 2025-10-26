package com.example.stockswiper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockSwiperApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockSwiperApplication.class, args);
    }
}