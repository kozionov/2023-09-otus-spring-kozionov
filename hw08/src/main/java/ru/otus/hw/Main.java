package ru.otus.hw;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.GenreRepository;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(Main.class);

        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        GenreRepository genreRepository = context.getBean(GenreRepository.class);

        Thread.sleep(1000);

        System.out.println("\n\n\n----------------------------------------------\n\n");
        System.out.println("Авторы в БД:");
        authorRepository.findAll().forEach(p -> System.out.println(p.getFullName()));
        genreRepository.findAll().forEach(p -> System.out.println(p.getName()));
        System.out.println("\n\n----------------------------------------------\n\n\n");
    }
}
