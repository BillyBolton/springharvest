package dev.springharvest.library.config;

import dev.springharvest.testing.config.BeanConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {BeanConfig.class})
public class TestComponentScanningConfig {
}
