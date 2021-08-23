package com.learning.student.importerservice.integration.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentMsg {
    private String firstName;
    private String lastName;
    private String cnp;
    private String dateOfBirth;
    private AddressMsg address;
    private List<GradeMsg> grades;
}
