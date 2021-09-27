package com.learning.student.importerservice.integration.queue;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.test.util.AssertionUtils;
import com.learning.student.importerservice.test.util.ImporterTestData;
import com.learning.student.importerservice.util.XmlToJsonTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link XmlToJsonTransformer}
 */
class XmlToJsonTransformerTest {

    public static final String TEST_FILE_PATH = "src/test/resources/test-student.xml";

    private XmlToJsonTransformer xmlToJsonTransformer;

    @BeforeEach
    void setUp() {
        xmlToJsonTransformer = new XmlToJsonTransformer();
    }

    @Test
    void transformStudentToXmlIsReturningValidStudent() {
        // given
        Student expectedStudent = ImporterTestData.getStudent();

        // when
        Student actualStudent = xmlToJsonTransformer.transform(TEST_FILE_PATH);

        // then
        AssertionUtils.assertStudents(expectedStudent, actualStudent);
    }
}
