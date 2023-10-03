package ru.otus.spring.dao;

import ru.otus.spring.model.TestUnit;

import java.util.List;

public interface TestDao {
    List<TestUnit> getTestUnits();
}
