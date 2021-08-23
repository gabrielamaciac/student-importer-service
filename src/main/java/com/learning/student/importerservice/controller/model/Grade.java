package com.learning.student.importerservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Grade {
    //(discipline, date of grade, grade)
    private String subject;
    private String dateReceived;
    private int mark;
}
