package kogayushi.spring_webflux.and_rabbitmq_forrpc

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "demo")
class DemoProperties(
    @field: NotEmpty
    val applications: List<String>
)
