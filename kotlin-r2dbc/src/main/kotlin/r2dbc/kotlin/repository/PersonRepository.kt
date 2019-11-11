package r2dbc.kotlin.repository

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.flow.asFlow
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.stereotype.Repository
import r2dbc.kotlin.model.Person

@Repository
class PersonRepository(private val client: DatabaseClient) {

    suspend fun getById(id: Long): Person? = client.execute()
            .sql("select id, name, age from person where id=$1")
            .bind(0, id)
            .`as`(Person::class.java)
            .fetch()
            .one()
            .awaitFirstOrNull()

    @FlowPreview
    suspend fun findAll(): Flow<Person> = client.execute()
            .sql("select id, name, age from person where deleted_at is null")
            .`as`(Person::class.java)
            .fetch()
            .all()
            .asFlow()
}