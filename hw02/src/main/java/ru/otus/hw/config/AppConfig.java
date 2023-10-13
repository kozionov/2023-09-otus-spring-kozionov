package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${test.rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    @Value("${test.fileName}")
    private String testFileName;

    @Bean
    public TestConfig testConfig() {
        return new TestConfig() {
            @Override
            public int getRightAnswersCountToPass() {
                return rightAnswersCountToPass;
            }
        };
    }

    @Bean
    public TestFileNameProvider testFileNameProvider() {
        return new TestFileNameProvider() {
            @Override
            public String getTestFileName() {
                return testFileName;
            }
        };
    }

}
