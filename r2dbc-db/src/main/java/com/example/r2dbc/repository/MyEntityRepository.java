package com.example.r2dbc.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEntityRepository extends ReactiveCrudRepository<MyEntity,Long> {

}
