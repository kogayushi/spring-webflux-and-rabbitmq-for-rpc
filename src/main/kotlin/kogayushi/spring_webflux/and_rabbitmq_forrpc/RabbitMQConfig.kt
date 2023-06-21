package kogayushi.spring_webflux.and_rabbitmq_forrpc

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig(
    private val amqpAdmin: AmqpAdmin,
    private val demoProperties: DemoProperties,
) {

    companion object {
        private const val topicExchangeName = "demo.exchange"

        private val NUMBER_LIST = ('0'..'9').toList()
        private val ALPHABET_LIST = ('a'..'f').toList()
        private val HEX_CHARACTER_SET = NUMBER_LIST + ALPHABET_LIST
        const val LEADING_CHARACTER_COUNT = 2
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(topicExchangeName)
    }

    // for load balancing, define destination queues using the leading characters of the UUID.
    @Bean
    fun queues(): List<Queue> {
        val queues = demoProperties.applications.flatMap { application ->
            val combinations = generateHexCharacterCombinations(HEX_CHARACTER_SET, "", LEADING_CHARACTER_COUNT)

            combinations.map { aCombination ->
                Queue("$application.$aCombination", true, false, true)
            }

        }

        queues.forEach { queue ->
            amqpAdmin.declareQueue(queue)
        }

        return queues
    }

    // generate a list of all possible combinations of hexadecimal characters.
    fun generateHexCharacterCombinations(characters: List<Char>, current: String, maxLength: Int, combinations: List<String> = listOf()): List<String> {
        return if (current.length == maxLength) {
            combinations + current
        } else {
            characters.fold(combinations) { acc, char ->
                generateHexCharacterCombinations(characters, current + char, maxLength, acc)
            }
        }
    }

    //  define bindings to deliver to the destination queues that previously defined.
    @Bean
    fun bindings(): List<Binding> {
        val bindings = this.queues().map { queue ->
            val routingKey = queue.name
            BindingBuilder.bind(queue).to(this.exchange()).with(routingKey)
        }

        bindings.forEach { binding ->
            amqpAdmin.declareBinding(binding)
        }

        return bindings
    }
}

