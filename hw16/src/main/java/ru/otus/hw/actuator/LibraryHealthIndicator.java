package ru.otus.hw.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.BookService;

@Component
@RequiredArgsConstructor
public class LibraryHealthIndicator implements HealthIndicator {
    private static final int HOURS_COUNT = 12;

    private final BookService bookService;

    @Override
    public Health health() {
        long bookCount = bookService.findAll().stream().count();
        if (bookCount == 0) {
            String message = String.format("В библиотеке отсутствуют книги");
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", message)
                    .build();
        } else {
            String message = String.format("Все в порядке. Есть что почитать.");
            return Health.up()
                    .withDetail("message", message)
                    .build();
        }
    }
}
