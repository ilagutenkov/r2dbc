package com.example.r2dbc.service;


import com.example.r2dbc.model.PgStatAll;
import com.example.r2dbc.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;


    public Mono<PgStatAll> getPersonsAge() {
        return personRepository
                .findPeople()
//                .publishOn(Schedulers.parallel())
                .filter(person -> person.getAge() != null && person.getAge() < 50)
                .collectList()
                .map(personList -> new PgStatAll(personList
                        .stream()
                        .mapToLong(person -> Long.valueOf(person.getAge()))
                .sum()));
    }
}
