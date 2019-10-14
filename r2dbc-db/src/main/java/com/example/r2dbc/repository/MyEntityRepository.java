package com.example.r2dbc.repository;

import com.example.dbmodel.MyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEntityRepository extends ReactiveCrudRepository<MyEntity,Long> {

}
