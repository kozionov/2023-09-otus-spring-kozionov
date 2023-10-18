package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@DisplayName("Тест CsvQuestionDao")
public class CsvQuestionDaoTest {

    @Test
    public void readFileTest() {
        TestFileNameProvider testFileNameProvider = mock(TestFileNameProvider.class);
        doReturn("/test.csv").when(testFileNameProvider).getTestFileName();
        QuestionDao questionDao = new CsvQuestionDao(testFileNameProvider);
        List<Question> questions = questionDao.findAll();
        assertEquals("Question1", questions.get(0).text());
        assertEquals("Question5", questions.get(4).text());
    }
}
