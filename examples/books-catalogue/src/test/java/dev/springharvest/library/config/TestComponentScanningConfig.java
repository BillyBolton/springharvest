package dev.springharvest.library.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"dev.springharvest.testing"})
public class TestComponentScanningConfig {

}
