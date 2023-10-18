package ru.otus.hw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;
import java.util.Map;

@ConfigurationProperties(prefix = "test")
public class AppProps implements TestConfig, TestFileNameProvider, LocaleConfig {

    private int rightAnswersCountToPass;

    private Locale locale;

    private Map<String, String> fileNameByLocaleTag;

    @ConstructorBinding
    public AppProps(int rightAnswersCountToPass, Locale locale, Map<String, String> fileNameByLocaleTag) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.locale = locale;
        this.fileNameByLocaleTag = fileNameByLocaleTag;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
