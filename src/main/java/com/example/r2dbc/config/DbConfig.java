package com.example.r2dbc.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@RequiredArgsConstructor
@EnableR2dbcRepositories(basePackages={"com.example.r2dbc.repository"})
public class DbConfig extends AbstractR2dbcConfiguration {

    private final ConnectionFactory connectionFactory;


  @Override
  public ConnectionFactory connectionFactory(){
      return connectionFactory;
  }

//    @Bean
//    public ReactiveDataAccessStrategy reactiveDataAccessStrategy(){
//        return AbstractR2dbcConfiguration.
//    }






}
