package com.example.syncdb.controller;


import com.example.dbmodel.PgStatAll;
import com.example.syncdb.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MainSyncController {

    private final PersonService personService;


    @GetMapping("/sumAge")
    public PgStatAll getPersonsAge() {
        return personService.getPersonAge();
    }

}
