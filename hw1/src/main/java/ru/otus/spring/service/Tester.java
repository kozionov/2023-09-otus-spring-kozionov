package ru.otus.spring.service;

import ru.otus.spring.dao.TestDao;
import ru.otus.spring.model.TestUnit;

import java.util.List;

public class Tester {
    private TestDao testDao;

    public Tester(TestDao testDao) {
        this.testDao = testDao;
    }

    public void start() {
        List<TestUnit> testUnits = testDao.getTestUnits();
        testUnits.forEach(System.out::println);
    }
}
