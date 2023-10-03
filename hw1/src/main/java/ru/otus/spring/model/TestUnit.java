package ru.otus.spring.model;

import java.util.List;

public class TestUnit {
    private String question;

    private List<String> answers;

    public TestUnit() {
    }

    public TestUnit(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return question + '\n' + answers;
    }
}
