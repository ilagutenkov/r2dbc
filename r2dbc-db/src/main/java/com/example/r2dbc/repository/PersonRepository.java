package com.example.r2dbc.repository;


import com.example.r2dbc.model.PersonReactive;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonReactive,Long> {

    @Query("select id, name from public.person e where e.name = $1")
    Flux<PersonReactive> findByName(String name) ;

    @Query("select id, name, age from public.person where deleted_at is null")
    Flux<PersonReactive> findPeople() ;

}
