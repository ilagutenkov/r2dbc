package r2dbc.kotlin.service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import r2dbc.kotlin.model.PersonAggr
import r2dbc.kotlin.repository.PersonRepository
import java.time.Instant

@Service
@Slf4j
class PersonService(private val personRepository: PersonRepository) {

    suspend fun getAggregate(): PersonAggr {
        var start = Instant.now().toEpochMilli()

        var sum = personRepository
                .findAll()
//                .also { var end=Instant.now().toEpochMilli()
//                    var dif=end-start
//                println("query time $dif")
//                }
                .filter { it.age < 50 }
                .map {
                    //                    var checkpoint=Instant.now().toEpochMilli()-start
//                    println("checkpoint $checkpoint")
                    it.age
                }
                .reduce { a, b -> a + b }

        var elapsed = Instant.now().toEpochMilli() - start
        println("total time $elapsed")

        return PersonAggr(sum.toLong())
    }

    suspend fun manyQueries(): List<PersonAggr> {
        val aggregateChannel = Channel<PersonAggr>()
        var cnt = 10
        var log=LoggerFactory.getLogger(this.javaClass)

        repeat(cnt) {
            GlobalScope.async {
             log.info("async go")
                aggregateChannel.send(getAggregate())
            }
        }

        var result = ArrayList<PersonAggr>()

        repeat(cnt) {
            result.add(aggregateChannel.receive())
        }

        return result

    }


}