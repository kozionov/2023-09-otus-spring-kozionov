package ru.otus.hw.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import ru.otus.hw.service.ButterflyService;

import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class TransformFlowConfig {

    @Bean
    public MessageChannel caterpillarChannel() {
        return MessageChannels.queue(100).getObject();
    }

    @Bean
    public PublishSubscribeChannel butterflyChannel() {
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean
    public IntegrationFlow transformationFlow(ButterflyService butterflyService) {
        return IntegrationFlow.from(caterpillarChannel())
                .channel(MessageChannels.executor(Executors.newFixedThreadPool(10)))
                .handle(butterflyService, "transformation")
                .channel(butterflyChannel())
                .get();
    }
}