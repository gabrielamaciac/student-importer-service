package com.learning.student.importerservice.service.impl;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.integration.queue.StudentServiceSender;
import com.learning.student.importerservice.service.ImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImporterServiceImpl implements ImporterService {

    @Autowired
    public StudentServiceSender studentServiceSender;

    public ImporterServiceImpl(StudentServiceSender studentServiceSender) {
        this.studentServiceSender = studentServiceSender;
    }

    @Override
    public void importStudent(Student student) {
        studentServiceSender.convertAndSend(student);
    }
}
