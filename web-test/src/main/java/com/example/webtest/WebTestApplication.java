package com.example.webtest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping
@SpringBootApplication
public class WebTestApplication {

    private static void accept(Throwable er) {
        log.error("", er);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper()))
                .setConnectTimeout(Duration.ofSeconds(50))
                .setReadTimeout(Duration.ofSeconds(50))
                .build();
    }

    public static void main(String[] args) {

        SpringApplication.run(WebTestApplication.class, args);


    }

//    @PostConstruct
//    public void init() throws Exception {
//        boolean sync = false;
//
//
//        runTests(sync);
////        runWebClientTest(sync);
//
////        Thread.sleep(20000);
//    }

    private void logThread(Object... msg) {

        log.info("thread: {} object {}", Thread.currentThread(), msg);

    }


    @GetMapping("web-client")
    private Mono<Long> runWebClientTest(@RequestParam boolean sync, @RequestParam(defaultValue = "30") int cnt) {
        String port = "8882";
        if (sync)
            port = "8883";

        WebClient webClient = WebClient.builder()
                .baseUrl("localhost:" + port + "/sumAge")
                .build();


        Date start = new Date();


        return Flux.range(0, cnt)
                .flatMap(i ->
                                webClient
                                        .get()
                                        .retrieve()
                                        .bodyToMono(PgStatAll.class)
//                            .doOnNext(it->this.logThread("next web client"))
                )
//                    .doOnNext(it->this.logThread("next flux"))
                .then(Mono.just(new Date().getTime() - start.getTime()));
    }


    @GetMapping("rest")
    private Long runTests(@RequestParam(defaultValue = "8000") int port
            , @RequestParam(defaultValue = "30") int cnt,
                          @RequestParam(defaultValue = "false") boolean parallel) {
        RestTemplate rt = restTemplate();

        Date start = new Date();

        String path = "/manyQueries";


        for (int i = 0; i < cnt; i++) {
            String pathFull = "http://localhost:" + port + path;
//            log.info("path {}",pathFull);
            Object stat = rt.getForEntity(pathFull,Object.class).getBody();

        }

        Date end = new Date();
        log.info("total time template {}", end.getTime() - start.getTime());

        return end.getTime() - start.getTime();

    }


    private CompletableFuture<Void> asyncTask(Runnable runnable) {
        CompletableFuture<Void> tas = CompletableFuture.runAsync(() -> runnable.run())
                .exceptionally(ex -> {
                    log.error("problems", ex);
                    return null;
                });

        return tas;

    }

}
