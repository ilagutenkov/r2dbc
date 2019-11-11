package r2dbc.kotlin.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import r2dbc.kotlin.model.Person
import r2dbc.kotlin.model.PersonAggr
import r2dbc.kotlin.repository.PersonRepository
import r2dbc.kotlin.service.PersonService

@RestController
class MainController {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Autowired
    lateinit var personService: PersonService

    @GetMapping("get")
    suspend fun getById(@RequestParam id: Long): Person? {
        return personRepository.getById(id)
    }

    @GetMapping("/sumAge")
    suspend fun sumAge() =personService.getAggregate()

    @GetMapping("manyQueries")
    suspend fun manyQueries(): List<PersonAggr> =personService.manyQueries()



}