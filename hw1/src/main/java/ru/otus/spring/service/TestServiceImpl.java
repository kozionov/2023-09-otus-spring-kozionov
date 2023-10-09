package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.List;
import java.util.stream.Collectors;

public class TestServiceImpl implements TestService {

    private IOService ioService;

    private QuestionDao questionDao;

    public TestServiceImpl(IOService ioService, QuestionDao questionDao) {
        this.ioService = ioService;
        this.questionDao = questionDao;
    }

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = questionDao.findAll();
        for (Question q : questions) {
            List<Answer> answers = q.getAnswers();
            List<String> s = answers.stream()
                    .map(x -> x.getText())
                    .collect(Collectors.toList());
            ioService.printFormattedLine(q.getQuestion());
            ioService.printFormattedLine(s.toString());

        }
    }
}
