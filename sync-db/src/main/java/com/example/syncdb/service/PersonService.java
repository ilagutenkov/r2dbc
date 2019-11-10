package com.example.syncdb.service;

import com.example.dbmodel.Person;
import com.example.dbmodel.PgStatAll;
import com.example.syncdb.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    public PgStatAll getPersonAge() {
        List<Person> a = getPeople();

        return getPgStatAll(a);
    }

    private List<Person> getPeople() {
        Instant start = Instant.now();
        List<Person> a = personRepository
                .findAll();
        Instant elapsed = Instant.now();
        long tm = elapsed.toEpochMilli() - start.toEpochMilli();
        log.info("query time: {}", tm);
        return a;
    }

    private PgStatAll getPgStatAll(List<Person> a) {
        return new PgStatAll(a
                .stream()
                .filter(person -> person.getAge() != null && person.getAge() < 50)
                .mapToLong(person -> Long.valueOf(person.getAge()))
                .sum());
    }


    @SneakyThrows
    public List<PgStatAll> manyQieries() {
        var futures = new ArrayList<CompletableFuture<PgStatAll>>();

        for (int i = 0; i < 10; i++) {
            var future = CompletableFuture.supplyAsync(() -> {
                List<Person> people = getPeople();
                return getPgStatAll(people);
            });

            futures.add(future);
        }

        CompletableFuture[] array = (CompletableFuture[]) Array.newInstance(CompletableFuture.class, 10);

        return CompletableFuture
                .allOf(futures.toArray(array))
                .thenApply((f) -> {
                    return futures
                            .stream()
                            .map(fut -> {
                                try {
                                    return fut.get();
                                } catch (InterruptedException e) {
                                    return null;
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            })
                            .collect(Collectors.toList());
                }).get();
    }
}
