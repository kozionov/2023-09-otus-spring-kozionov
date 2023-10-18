package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String MESSAGE = "TestService.error";

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var answerNum = askQuestion(question);
            testResult.applyAnswer(question, question.answers().get(answerNum - 1).isCorrect());
        }
        return testResult;
    }

    private int askQuestion(Question question) {
        ioService.printLine(question.text());
        String answers = question.answers().stream().map(Answer::text).collect(Collectors.joining(String.format("%n")));
        var answerMaxNum = question.answers().size();
        return ioService.readIntForRangeWithPromptLocalizedError(1, answerMaxNum, answers, MESSAGE);
    }
}
