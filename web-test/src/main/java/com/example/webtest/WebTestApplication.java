package com.example.webtest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
@Slf4j
public class WebTestApplication {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder()
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper()))
                .build();
    }

    public static void main(String[] args) {

        SpringApplication.run(WebTestApplication.class, args);


    }

    @PostConstruct
    public void init() throws Exception{
        runTests(false);
        runTests(false);
        runTests(false);

        Thread.sleep(5000);
    }

    private void logThread(Object ... msg){

        log.info("thread: {} object {}", Thread.currentThread(), msg);

    }

    public void runTests(boolean sync){
        RestTemplate rt=restTemplate();

        String port="8882";
        if(sync)
        port="8883";

        String finalPort = port;
        CompletableFuture.runAsync(()->{
            Instant start=Instant.now();
            PgStatAll stat= rt.getForEntity("http://localhost:"+ finalPort +"/sumAge", PgStatAll.class).getBody();
            Instant end=Instant.now();

            logThread("async","time: "+ (end.toEpochMilli()-start.toEpochMilli()), stat);
        })
        .exceptionally(ex->{
            log.error("problems",ex);
            return null;
        });


    }

}
