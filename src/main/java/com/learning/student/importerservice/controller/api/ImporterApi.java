package com.learning.student.importerservice.controller.api;

import com.learning.student.importerservice.controller.model.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ImporterApi {
    @PostMapping("/import")
    ResponseEntity<String> sendStudent(@RequestBody StudentDto studentDto);
}
