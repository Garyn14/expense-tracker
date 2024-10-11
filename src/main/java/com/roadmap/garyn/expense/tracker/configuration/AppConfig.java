package com.roadmap.garyn.expense.tracker.configuration;

import com.roadmap.garyn.expense.tracker.exception.CLIExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    CLIExceptionResolver  cliExceptionResolver() {
        return new CLIExceptionResolver();
    }
}
