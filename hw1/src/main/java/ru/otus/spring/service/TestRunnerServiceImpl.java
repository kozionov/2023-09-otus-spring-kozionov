package ru.otus.spring.service;

public class TestRunnerServiceImpl implements TestRunnerService {
    private final TestService testService;

    public TestRunnerServiceImpl(TestService testService) {
        this.testService = testService;
    }

    public void start() {
        testService.executeTest();
    }
}
