package com.learning.student.importerservice.util;

import com.learning.student.importerservice.integration.model.Student;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class XmlToJsonTransformer {

    public Student transform(String path) {
        CustomXmlParser customXmlParser = new CustomXmlParser(new File(path));
        Student studentFromXml = customXmlParser.getStudentFromXml();
        log.info("Received XML file for student:  " + studentFromXml.getFirstName());
        return studentFromXml;
    }
}
