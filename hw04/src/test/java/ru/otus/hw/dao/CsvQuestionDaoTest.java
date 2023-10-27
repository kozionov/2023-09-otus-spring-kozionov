package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.LocaleConfig;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.LocalizedIOService;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@DisplayName("Тест CsvQuestionDao")
@SpringBootTest
public class CsvQuestionDaoTest {

    @MockBean
    private TestFileNameProvider testFileNameProvider;

    @Autowired
    private CsvQuestionDao questionDao;
    @MockBean
    private LocaleConfig localeConfig;
    @MockBean
    private TestConfig testConfig;

    @Test
    public void readFileTest() {
        doReturn("/test.csv").when(testFileNameProvider).getTestFileName();
        doReturn(new Locale("en_US")).when(localeConfig).getLocale();
        doReturn(3).when(testConfig).getRightAnswersCountToPass();
        List<Question> questions = questionDao.findAll();
        assertEquals("Question1", questions.get(0).text());
        assertEquals("Question5", questions.get(4).text());
    }
}
