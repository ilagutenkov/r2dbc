package com.example.r2dbc.repository;


import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveCrudRepository<Person,Long> {

    @Query("select id, name from public.person e where e.name = $1")
    Flux<Person> findByName(String name) ;


}
