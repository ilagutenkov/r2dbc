package r2dbc.kotlin.config

import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter
import r2dbc.kotlin.handler.PersonHandler

@Configuration
class RouterConfiguration {

    @FlowPreview
    @Bean
    fun productRoutes(personHandler: PersonHandler) = coRouter {
        GET("/", personHandler::findOne)
//        GET("/{id}/stock", personHandler::findMany)
    }
}