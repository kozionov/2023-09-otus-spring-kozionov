package ru.otus.spring.mapper;

import ru.otus.spring.model.TestUnit;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestUnitMapper {
    public static TestUnit transform(String[] csvLine) {
        TestUnit testUnit = new TestUnit();
        testUnit.setQuestion(csvLine[0]);
        testUnit.setAnswers(Arrays.asList(csvLine).stream().skip(1).collect(Collectors.toList()));
        return testUnit;
    }
}
