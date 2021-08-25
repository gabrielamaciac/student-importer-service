package com.learning.student.importerservice.controller.api;

import com.learning.student.importerservice.controller.model.StudentDto;
import com.learning.student.importerservice.facade.ImporterFacadeImpl;
import com.learning.student.importerservice.integration.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class ImporterController implements ImporterApi {

    @Autowired
    public ImporterFacadeImpl importerFacade;

    public ModelMapper modelMapper = new ModelMapper();

    @Override
    @PostMapping("/import")
    public ResponseEntity<String> sendStudent(@RequestBody StudentDto studentDto) {
        importerFacade.importStudent(modelMapper.map(studentDto, Student.class));
        return ResponseEntity.ok("Student JSON sent to queue.");
    }
}