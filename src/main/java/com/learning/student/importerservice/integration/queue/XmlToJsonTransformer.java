package com.learning.student.importerservice.integration.queue;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.test.util.CustomXmlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class XmlToJsonTransformer {

    public Student transform(String path) {
        CustomXmlParser customXmlParser = new CustomXmlParser(new File(path));
        Student studentFromXml = customXmlParser.getStudentFromXml();
        log.info("Received XML file for student:  " + studentFromXml.getFirstName());
        return studentFromXml;
    }
}
