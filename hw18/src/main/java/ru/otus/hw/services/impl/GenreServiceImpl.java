package ru.otus.hw.services.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @CircuitBreaker(name = "randomActivity", fallbackMethod = "fallbackRandomActivity")
    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        exceptionRandomly();
        return genreRepository.findAll();
    }

    public List<Genre> fallbackRandomActivity(Throwable throwable) {
        Genre rent = new Genre();
        rent.setId(0L);
        rent.setName("N/A");
        List<Genre> rents = new ArrayList<>();
        rents.add(rent);
        return rents;
    }

    private void exceptionRandomly() {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        System.out.println("randomNum = " + randomNum);
        if (randomNum == 3) {
            System.out.println("It is a chance for demonstrating CircuitBreaker action");
            throw new RuntimeException();
        }
    }
}
