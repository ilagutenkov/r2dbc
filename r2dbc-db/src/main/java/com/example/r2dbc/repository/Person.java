package com.example.r2dbc.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Person {

    @Id
    private Long id;

    private String name;
}
