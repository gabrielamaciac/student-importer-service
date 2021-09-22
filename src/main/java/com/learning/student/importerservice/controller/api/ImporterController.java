package com.learning.student.importerservice.controller.api;

import com.learning.student.importerservice.controller.model.StudentDto;
import com.learning.student.importerservice.facade.ImporterFacade;
import com.learning.student.importerservice.integration.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImporterController implements ImporterApi {

    private final ImporterFacade importerFacade;
    private final ModelMapper modelMapper;

    public ImporterController(ImporterFacade importerFacade, ModelMapper modelMapper) {
        this.importerFacade = importerFacade;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<String> sendStudent(StudentDto studentDto) {
        importerFacade.importStudent(modelMapper.map(studentDto, Student.class));
        return new ResponseEntity<>("Student JSON sent to queue.", HttpStatus.OK);
    }
}