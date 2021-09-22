package com.learning.student.importerservice.test.util;

import com.learning.student.importerservice.controller.model.AddressDto;
import com.learning.student.importerservice.controller.model.GradeDto;
import com.learning.student.importerservice.controller.model.MarkDto;
import com.learning.student.importerservice.controller.model.StudentDto;
import com.learning.student.importerservice.integration.model.Address;
import com.learning.student.importerservice.integration.model.Grade;
import com.learning.student.importerservice.integration.model.Mark;
import com.learning.student.importerservice.integration.model.Student;

import java.util.Collections;

public class ImporterTestData {
    public static final String TEST_FIRST_NAME = "TestFirstName";
    public static final String TEST_LAST_NAME = "TestLastName";
    public static final String DATE_OF_BIRTH = "1987-12-10";
    public static final String TEST_CITY = "TestCity";
    public static final String TEST_COUNTRY = "TestCountry";
    public static final String TEST_NUMBER = "TestNumber";
    public static final String TEST_STREET = "TestStreet";
    public static final String TEST_SUBJECT = "TestSubject";
    public static final String DATE_RECEIVED = "2020-03-12";
    public static final String TEST_CNP = "TestCnp";

    public static StudentDto getStudentDto() {
        AddressDto address = new AddressDto(TEST_STREET, TEST_NUMBER, TEST_CITY, TEST_COUNTRY);
        MarkDto mark = new MarkDto(DATE_RECEIVED, 10.0);
        GradeDto grade = new GradeDto(TEST_SUBJECT, Collections.singletonList(mark));
        return new StudentDto(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_CNP, DATE_OF_BIRTH, address, Collections.singletonList(grade));
    }

    public static Student getStudent() {
        Address address = new Address(TEST_STREET, TEST_NUMBER, TEST_CITY, TEST_COUNTRY);
        Mark mark = new Mark(DATE_RECEIVED, 10.0);
        Grade grade = new Grade(TEST_SUBJECT, Collections.singletonList(mark));
        return new Student(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_CNP, DATE_OF_BIRTH, address, Collections.singletonList(grade));
    }
}
