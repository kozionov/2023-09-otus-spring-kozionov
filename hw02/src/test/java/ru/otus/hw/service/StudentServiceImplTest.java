package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class StudentServiceImplTest {

    @Test
    public void determineCurrentStudentTest() {
        IOService ioService = mock(IOService.class);
        StudentServiceImpl studentService = new StudentServiceImpl(ioService);
        doReturn("name").when(ioService).readStringWithPrompt("Please input your first name");
        Student student = studentService.determineCurrentStudent();
        assertEquals("name", student.firstName());
    }
}
