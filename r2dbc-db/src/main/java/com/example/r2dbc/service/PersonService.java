package com.example.r2dbc.service;


import com.example.r2dbc.model.PersonReactive;
import com.example.r2dbc.model.PgStatAll;
import com.example.r2dbc.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    private static boolean test(PersonReactive person) {
        return person.getAge() != null && person.getAge() < 50;
    }

    private static long applyAsLong(PersonReactive person) {
        return Long.valueOf(person.getAge());
    }

    private static PgStatAll apply(List<PersonReactive> personList) {
        return new PgStatAll(personList
                .stream()
                .mapToLong(PersonService::applyAsLong)
                .sum());
    }

    public Mono<PgStatAll> getPersonsAge() {

        return personRepository
                .findPeople()
//                .publishOn(Schedulers.parallel())
                .filter(PersonService::test)
                .collectList()
                .map(PersonService::apply);
    }

    public Mono<PgStatAll> test() {
        return Flux
                .range(0, 10)
                .map(i -> PersonReactive.builder().age(i).id(0L).name("no").build())
                .filter(person -> person.getAge() != null && person.getAge() < 50)
                .collectList()
                .map(personList -> new PgStatAll(personList
                        .stream()
                        .mapToLong(person ->
                                Long.valueOf(person.getAge()))
                        .sum()));
    }

}
