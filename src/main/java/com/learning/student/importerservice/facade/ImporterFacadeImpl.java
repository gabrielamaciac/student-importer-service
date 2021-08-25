package com.learning.student.importerservice.facade;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.service.ImporterService;
import org.springframework.stereotype.Component;

@Component
public class ImporterFacadeImpl implements ImporterFacade {

    private ImporterService importerService;

    public ImporterFacadeImpl(ImporterService importerService) {
        this.importerService = importerService;
    }

    @Override
    public void importStudent(Student student) {
        importerService.importStudent(student);
    }
}
