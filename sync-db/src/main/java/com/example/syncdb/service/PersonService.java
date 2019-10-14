package com.example.syncdb.service;

import com.example.dbmodel.PgStatAll;
import com.example.syncdb.repository.MyEntitySyncRepository;
import com.example.syncdb.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public PgStatAll getPersonAge() {
        return new PgStatAll(personRepository
                .findAll()
                .stream()
                .filter(person -> person.getAge() != null && person.getAge() < 50)
                .mapToLong(person -> Long.valueOf(person.getAge()))
                .sum());
    }

}
