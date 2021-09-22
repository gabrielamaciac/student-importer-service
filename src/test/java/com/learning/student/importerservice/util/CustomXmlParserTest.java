package com.learning.student.importerservice.util;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.test.util.AssertionUtils;
import com.learning.student.importerservice.test.util.CustomXmlParser;
import com.learning.student.importerservice.test.util.ImporterTestData;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test for {@link CustomXmlParser}
 */
class CustomXmlParserTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test-student.xml";
    private final Student expectedStudent = ImporterTestData.getStudent();

    private CustomXmlParser customXmlParser;

    @Test
    void getStudentFromXmlReturnsValidStudent() {
        customXmlParser = new CustomXmlParser(new File(TEST_FILE_PATH));

        Student actualStudent = customXmlParser.getStudentFromXml();

        AssertionUtils.assertStudents(expectedStudent, actualStudent);
    }

    @Test
    void getStudentFromXmlReturnsNull() {
        customXmlParser = new CustomXmlParser(new File("invalid file"));

        Student actualStudent = customXmlParser.getStudentFromXml();

        assertNull(actualStudent);
    }
}
