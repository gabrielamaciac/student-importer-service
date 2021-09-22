package com.learning.student.importerservice.service;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.integration.queue.StudentServiceSender;
import com.learning.student.importerservice.service.impl.ImporterServiceImpl;
import com.learning.student.importerservice.test.util.ImporterTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class ImporterServiceTest {

    private StudentServiceSender studentServiceSender;
    private ImporterService importerService;

    @BeforeEach
    void setUp() {
        studentServiceSender = mock(StudentServiceSender.class);
        importerService = new ImporterServiceImpl(studentServiceSender);
    }

    @Test
    void importStudentIsSuccessful() {
        Student student = ImporterTestData.getStudent();
        importerService.importStudent(student);

        verify(studentServiceSender).convertAndSend(student);
    }

    @Test
    void importStudentIsSkipped() {
        Student student = ImporterTestData.getStudent();

        verify(studentServiceSender, never()).convertAndSend(student);
    }
}
