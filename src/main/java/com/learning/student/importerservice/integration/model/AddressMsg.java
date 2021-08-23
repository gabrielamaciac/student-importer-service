package com.learning.student.importerservice.integration.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressMsg {
    private String street;
    private String number;
    private String city;
    private String country;
}
