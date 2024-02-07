package ru.otus.hw.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.model.Butterfly;
import ru.otus.hw.model.Caterpillar;

@MessagingGateway
public interface TransformGateway {

    @Gateway(requestChannel = "caterpillarChannel", replyChannel = "butterflyChannel")
    Butterfly process(Caterpillar caterpillar);
}