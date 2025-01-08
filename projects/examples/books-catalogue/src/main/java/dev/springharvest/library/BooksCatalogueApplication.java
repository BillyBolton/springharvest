package dev.springharvest.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"dev.springharvest.library", "dev.springharvest.shared"})
@ComponentScan(basePackages = {"dev.springharvest.library", "dev.springharvest.shared", "dev.springharvest.expressions"})
public class BooksCatalogueApplication {

  public static void main(String[] args) {
    SpringApplication.run(BooksCatalogueApplication.class, args);
  }
}
