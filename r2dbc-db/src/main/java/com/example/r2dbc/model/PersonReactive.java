package com.example.r2dbc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonReactive {

    @Id
    private Long id;

    private String name;


    private Integer age;
}

