package dev.springharvest.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"dev.springharvest.library", "dev.springharvest.shared"})
@ComponentScan(basePackages = {"dev.springharvest.library", "dev.springharvest.shared"})
public class BooksCatalogueCodegenApplication {

  public static void main(String[] args) {
    SpringApplication.run(BooksCatalogueCodegenApplication.class, args);
  }

}