package com.learning.student.importerservice.test.util;

import com.learning.student.importerservice.integration.model.Student;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionUtils {
    private AssertionUtils() {
    }

    public static void assertStudents(Student expected, Student actual) {
        assertAll(() -> assertEquals(expected.getFirstName(), actual.getFirstName()),
                () -> assertEquals(expected.getLastName(), actual.getLastName()),
                () -> assertEquals(expected.getCnp(), actual.getCnp()),
                () -> assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth()),
                () -> assertEquals(expected.getAddress().getCity(), actual.getAddress().getCity()),
                () -> assertEquals(expected.getAddress().getCountry(), actual.getAddress().getCountry()),
                () -> assertEquals(expected.getAddress().getNumber(), actual.getAddress().getNumber()),
                () -> assertEquals(expected.getAddress().getStreet(), actual.getAddress().getStreet()),
                () -> assertEquals(expected.getGrades().get(0).getSubject(), actual.getGrades().get(0).getSubject()),
                () -> assertEquals(expected.getGrades().get(0).getMarks().get(0).getDateReceived(), actual.getGrades().get(0).getMarks().get(0).getDateReceived()),
                () -> assertEquals(expected.getGrades().get(0).getMarks().get(0).getMark(), actual.getGrades().get(0).getMarks().get(0).getMark())
        );
    }
}
