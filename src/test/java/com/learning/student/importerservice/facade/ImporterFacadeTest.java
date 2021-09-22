package com.learning.student.importerservice.facade;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.service.ImporterService;
import com.learning.student.importerservice.test.util.ImporterTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Test for {@link ImporterFacade}
 */
class ImporterFacadeTest {
    private ImporterService importerService;
    private ImporterFacade importerFacade;

    @BeforeEach
    void setUp() {
        importerService = mock(ImporterService.class);
        importerFacade = new ImporterFacadeImpl(importerService);
    }

    @Test
    void importStudentIsSuccessful() {
        Student student = ImporterTestData.getStudent();
        importerFacade.importStudent(student);

        verify(importerService).importStudent(student);
    }

    @Test
    void importStudentIsSkipped() {
        Student student = ImporterTestData.getStudent();

        verify(importerService, never()).importStudent(student);
    }
}
