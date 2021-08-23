package com.learning.student.importerservice.integration.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeMsg {
    private String subject;
    private String dateReceived;
    private int mark;
}
