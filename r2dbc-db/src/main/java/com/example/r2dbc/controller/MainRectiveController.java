package com.example.r2dbc.controller;


import com.example.r2dbc.model.PgStatAll;
import com.example.r2dbc.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class MainRectiveController {

    private final PersonService personService;


    @GetMapping("/sumAge")
    public Mono<PgStatAll> getPersonsAge(){

        return personService
                .getPersonsAge();

    }

    @GetMapping("/manyQueries")
    public Flux<PgStatAll> manyQueries() {
        return personService.manyQueries();
    }

    @GetMapping("/test")
    public Mono<PgStatAll> test(){
        log.info("mainController test");

        return personService
                .test();

    }

}
