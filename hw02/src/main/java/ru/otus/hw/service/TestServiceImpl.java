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

    public static final String MESSAGE = "Error answer number";

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
            List<String> answers = question.answers().stream()
                    .map(x -> x.text())
                    .collect(Collectors.toList());
            var answerNum = ioService.readIntForRangeWithPrompt(1, answers.size(), answers.toString(), MESSAGE);
            testResult.applyAnswer(question, question.answers().get(answerNum - 1).isCorrect());
        }
        return testResult;
    }
}
