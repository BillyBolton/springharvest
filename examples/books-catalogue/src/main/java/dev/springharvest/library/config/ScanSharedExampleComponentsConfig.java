package dev.springharvest.library.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = {
    @ComponentScan(basePackages = {"dev.springharvest.shared"})
})
public class ScanSharedExampleComponentsConfig {

}
