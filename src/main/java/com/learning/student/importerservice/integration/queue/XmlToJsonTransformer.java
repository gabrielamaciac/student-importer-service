package com.learning.student.importerservice.integration.queue;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.learning.student.importerservice.integration.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class XmlToJsonTransformer {

    XmlMapper xmlMapper = new XmlMapper();

    public Student transform(String path) {
        try {
            //todo custom xml - use xPath
            Student student = xmlMapper.readValue(new File(path), Student.class);
            log.info("Received XML file for student:  " + student.getFirstName());
            return student;
        } catch (IOException e) {
            log.info("Exception occurred while converting the xml to json: " + e.getMessage());
        }
        return null;
    }
}
