package com.andrzejkarwoski;

import com.andrzejkarwoski.controller.FlashCardsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class FlashCardsAppBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(FlashCardsAppBootApplication.class, args);
        FlashCardsController controller = ctx.getBean(FlashCardsController.class);
        controller.mainLoop();
    }

    @Bean
    Scanner scanner() {
        return new Scanner(System.in);
    }

}
