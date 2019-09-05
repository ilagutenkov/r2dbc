package com.example.r2dbc.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository<Person,Long> {
}
