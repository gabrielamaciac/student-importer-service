package com.learning.student.importerservice.facade;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.service.ImporterService;
import org.springframework.stereotype.Component;

@Component
public class ImporterFacade {

    private ImporterService importerService;

    public ImporterFacade(ImporterService importerService) {
        this.importerService = importerService;
    }

    public void importStudent(Student student) {
        importerService.importStudent(student);
    }
}
