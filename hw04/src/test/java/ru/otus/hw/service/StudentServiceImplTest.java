package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.StudentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@DisplayName("Тест StudentServiceImpl")
@SpringBootTest
public class StudentServiceImplTest {
    @MockBean
    private LocalizedIOService ioService;

    @Autowired
    private StudentServiceImpl studentService;

    @Test
    public void determineCurrentStudentTest() {
        doReturn("name")
                .when(ioService)
                .readStringWithPromptLocalized("StudentService.input.first.name");
        Student student = studentService.determineCurrentStudent();
        assertEquals("name", student.firstName());
    }
}
