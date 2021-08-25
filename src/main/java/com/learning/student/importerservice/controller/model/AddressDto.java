package com.learning.student.importerservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
    //(street, number, city, country)
    private String street;
    private String number;
    private String city;
    private String country;
}
