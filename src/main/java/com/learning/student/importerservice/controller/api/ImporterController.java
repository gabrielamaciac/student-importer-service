package com.learning.student.importerservice.controller.api;

import com.learning.student.importerservice.controller.model.Student;
import com.learning.student.importerservice.integration.model.StudentMsg;
import com.learning.student.importerservice.integration.queue.JSONSender;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
public class ImporterController {

    @Autowired
    public JSONSender jsonSender;

    public ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/send")
    public ResponseEntity<String> sendStudent(@RequestBody Student student) {
        jsonSender.send(modelMapper.map(student, StudentMsg.class));
        return ResponseEntity.ok("Student sent to queue.");
    }
}