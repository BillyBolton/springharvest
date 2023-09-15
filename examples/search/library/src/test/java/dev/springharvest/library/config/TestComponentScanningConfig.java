package dev.springharvest.library.config;

import dev.springharvest.shared.config.ScanSharedExampleComponentsConfig;
import dev.springharvest.testing.config.ScanTestingLibraryComponentsConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {ScanTestingLibraryComponentsConfig.class, ScanSharedExampleComponentsConfig.class})
public class TestComponentScanningConfig {}
