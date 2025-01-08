package dev.springharvest.library.codegen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = {
    @ComponentScan(basePackages = {"dev.springharvest.expressions"}),
    @ComponentScan(basePackages = {"dev.springharvest.shared"}),
    @ComponentScan(basePackages = {"dev.springharvest.search"})
})
public class ScanSharedExampleComponentsConfig {

}
