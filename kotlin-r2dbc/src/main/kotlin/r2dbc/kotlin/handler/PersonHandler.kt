package r2dbc.kotlin.handler

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import r2dbc.kotlin.model.Person
import r2dbc.kotlin.repository.PersonRepository


@Component
class PersonHandler(private val repository: PersonRepository) {

    @FlowPreview
    suspend fun findOne(request: ServerRequest): ServerResponse {
        var id = request.queryParam("id")

        var personDef: Deferred<Person?> = GlobalScope.async { repository.getById(id.orElse("0").toLong()) }

        var result = personDef.await()


        return ServerResponse.ok().json().bodyAndAwait(flowOf(result!!))
//        return ServerResponse.ok().json().bodyValueAndAwait(result ?: Person(0, "default", 0))
    }

}
