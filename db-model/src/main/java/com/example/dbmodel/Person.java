package com.example.dbmodel;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Person {

    @Id
    private Long id;

    private String name;
}
