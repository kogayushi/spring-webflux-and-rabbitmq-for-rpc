package kogayushi.spring_webflux.and_rabbitmq_forrpc

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class DemoSubscriber {

    @RabbitListener(queues = ["#{@queues.![name]}"], ackMode = "MANUAL")
    fun rpc(requestMessage: String): Mono<String> {
        return Mono.just("Processed: $requestMessage").delayElement(Duration.ofMillis(250L))
    }
}
