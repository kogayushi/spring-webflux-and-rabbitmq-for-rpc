package kogayushi.spring_webflux.and_rabbitmq_forrpc

import kogayushi.spring_webflux.and_rabbitmq_forrpc.RabbitMQConfig.Companion.BYTE_COUNT
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*


@RestController
class DemoController(
    private val rabbitTemplate: RabbitTemplate,
) {

    @GetMapping("/reactive-api")
    fun reactiveApi(): Mono<String> {

        return Mono.just("Hello, World!").delayElement(Duration.ofMillis(250L))
    }

    @GetMapping("/blocking-api")
    fun blockingApi(): String {

        Thread.sleep(250L)

        return "Hello, World!"
    }
}

