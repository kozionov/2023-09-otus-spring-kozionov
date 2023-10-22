package hw.service;

import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.StudentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class StudentServiceImplTest {

    @Test
    public void determineCurrentStudentTest() {
        LocalizedIOService ioService = mock(LocalizedIOService.class);
        StudentServiceImpl studentService = new StudentServiceImpl(ioService);
        doReturn("name")
                .when(ioService)
                .readStringWithPromptLocalized("StudentService.input.first.name");
        Student student = studentService.determineCurrentStudent();
        assertEquals("name", student.firstName());
    }
}
