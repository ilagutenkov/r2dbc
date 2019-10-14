package com.example.r2dbc.repository;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RelationRepository extends ReactiveCrudRepository<PgStatAll,Long> {
    @Query("select  count(*) cnt from pg_stat_user_tables")
    Flux<PgStatAll> test();
}
