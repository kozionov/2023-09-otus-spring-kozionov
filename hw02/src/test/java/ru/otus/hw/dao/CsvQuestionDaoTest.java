package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест CsvQuestionDao")
public class CsvQuestionDaoTest {
    TestFileNameProvider testFileNameProvider = new TestFileNameProvider() {
        @Override
        public String getTestFileName() {
            return "/test.csv";
        }
    };
    QuestionDao questionDao = new CsvQuestionDao(testFileNameProvider);

    @Test
    public void readFileTest() {
        List<Question> questions = questionDao.findAll();
        assertEquals("Question1",questions.get(0).text());
        assertEquals("Question5",questions.get(4).text());
    }
}
