package com.example.dbmodel;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer age;

    @Basic
    @Column(name = "deleted_at")
    private java.sql.Timestamp deletedAt;
}
