package com.example.r2dbc.controller;


import com.example.dbmodel.PgStatAll;
import com.example.r2dbc.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MainRectiveController {

    private final PersonService personService;


    @GetMapping("/sumAge")
    public Mono<PgStatAll> getPersonsAge(){
        return personService
                .getPersonsAge();

    }

}
