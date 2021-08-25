package com.learning.student.importerservice.service.impl;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.integration.queue.StudentServiceGateway;
import com.learning.student.importerservice.service.ImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImporterServiceImpl implements ImporterService {

    @Autowired
    public StudentServiceGateway studentServiceGateway;

    public ImporterServiceImpl(StudentServiceGateway studentServiceGateway) {
        this.studentServiceGateway = studentServiceGateway;
    }

    @Override
    public void importStudent(Student student) {
        studentServiceGateway.convertAndSend(student);
    }
}
