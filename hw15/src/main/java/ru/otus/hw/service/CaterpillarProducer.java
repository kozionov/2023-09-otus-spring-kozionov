package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.otus.hw.integration.TransformGateway;
import ru.otus.hw.model.Butterfly;
import ru.otus.hw.model.Caterpillar;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class CaterpillarProducer {

    private final TransformGateway transformGateway;

    private final AtomicInteger counter = new AtomicInteger(0);

    @Async
    @Scheduled(fixedRate = 2000)
    public void start() {

        var caterpillar = new Caterpillar(counter.incrementAndGet());

        log.info("Появление гусеницы {}", caterpillar.getId());
        Butterfly butterfly = transformGateway.process(caterpillar);
        log.info("Гусеница {} превратилась в {}", caterpillar.getId(), butterfly.getSpecie());
    }
}