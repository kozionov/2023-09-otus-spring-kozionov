package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String MESSAGE = "Error answer number";

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            ioService.printLine(question.text());
            List<String> askQuestions = question.answers().stream()
                    .map(q -> q.text())
                    .collect(Collectors.toList());
            var answerMaxNum = askQuestions.size();
            var answerNum = ioService.readIntForRangeWithPrompt(1, answerMaxNum, askQuestions.toString(), MESSAGE);
            testResult.applyAnswer(question, question.answers().get(answerNum - 1).isCorrect());
        }
        return testResult;
    }
}
