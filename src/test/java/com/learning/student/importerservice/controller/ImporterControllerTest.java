package com.learning.student.importerservice.controller;

import com.learning.student.importerservice.controller.api.ImporterApi;
import com.learning.student.importerservice.controller.api.ImporterController;
import com.learning.student.importerservice.controller.model.StudentDto;
import com.learning.student.importerservice.facade.ImporterFacade;
import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.test.util.ImporterTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for {@link ImporterApi}
 */
class ImporterControllerTest {

    private ImporterFacade importerFacade;
    private ModelMapper modelMapper;
    private ImporterApi importerController;

    @BeforeEach
    void setUp() {
        importerFacade = mock(ImporterFacade.class);
        modelMapper = mock(ModelMapper.class);
        importerController = new ImporterController(importerFacade, modelMapper);
    }

    @Test
    void sendStudentToQueueReturns200OkStatusCode() {
        // Given
        StudentDto studentDto = ImporterTestData.getStudentDto();
        Student student = ImporterTestData.getStudent();
        when(modelMapper.map(studentDto, Student.class)).thenReturn(student);

        // When
        ResponseEntity<String> response = importerController.sendStudent(studentDto);

        // Then
        verify(importerFacade).importStudent(student);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
