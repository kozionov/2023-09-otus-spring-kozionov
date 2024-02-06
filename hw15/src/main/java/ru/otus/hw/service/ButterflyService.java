package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Butterfly;
import ru.otus.hw.model.Caterpillar;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ButterflyService {

    private static final Random random = new Random();

    public Butterfly transformation(Caterpillar caterpillar) {
        log.info( "Гусеница {} завернулась в кокон", caterpillar.getId());

        var delayTime = random.nextLong(1, 100);
        delay(delayTime);

        log.info( "Гусеница {} стала бабочкой", caterpillar.getId());
        return new Butterfly(receiveSpecie());
    }

    public String receiveSpecie() {
        List<String> givenList = Arrays.asList("Blue Morpho", "Black Swallowtail", "Chequered Skipper");
        Random rand = new Random();
        return givenList.get(rand.nextInt(givenList.size()));
    }

    private void delay(long time) {
        try {
            Thread.sleep(2000 + time * 100);
        } catch (Exception ignored) {}
    }
}