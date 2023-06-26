package kogayushi.spring_webflux.and_rabbitmq_forrpc

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "demo")
data class DemoProperties(
    @field: NotEmpty
    val appnameAndLeadingCharacterCount: List<AppnameAndLeadingCharacterCount>,
)

data class AppnameAndLeadingCharacterCount(
    val appname: String,
    val leadingCharacterCount: Int,
)
