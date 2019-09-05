package com.example.r2dbc.repository;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyEntityRepositoryTest {

    @Autowired
    private  MyEntityRepository myEntityRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void test() {
        MyEntity entity=new MyEntity();
        entity.setName("first");
//        entity.setId(0L);

        Mono<MyEntity> saveAction = myEntityRepository.save(entity);

        StepVerifier
                .create(saveAction)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void person(){
        Person entity=new Person();
        entity.setName("first");
//        entity.setId(0L);

        Mono<Person> saveAction = personRepository.save(entity);



        StepVerifier
                .create(saveAction)
                .expectNextCount(1)
                .verifyComplete();
    }

}