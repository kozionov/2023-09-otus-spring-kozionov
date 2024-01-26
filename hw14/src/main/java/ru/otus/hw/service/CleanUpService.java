package ru.otus.hw.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CleanUpService {

    @SuppressWarnings("unused")
    public void cleanUp() throws Exception {
        log.info("Выполняю завершающие мероприятия...");
        System.out.println("WORK");
        log.info("Завершающие мероприятия закончены");
    }
}
