package ru.otus.spring;

import org.junit.jupiter.api.Test;
import ru.otus.spring.mapper.TestUnitMapper;
import ru.otus.spring.model.TestUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUnitMapperTest {

    @Test
    void mapperTransform() {
        String[] csvLne = new String[]{"Q1", "A1", "A2"};
        TestUnit testUnit = TestUnitMapper.transform(csvLne);
        assertEquals("Q1", testUnit.getQuestion());
        assertEquals("A1", testUnit.getAnswers().get(0));
    }
}
