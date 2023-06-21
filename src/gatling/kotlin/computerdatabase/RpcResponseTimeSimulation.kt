package computerdatabase

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.CoreDsl.exec
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http

class RpcResponseTimeSimulation : Simulation() {

    private val api =
        exec(
            http("Rpc")
                .get("/rpc")
        )

    private val httpProtocol =
        http.baseUrl("http://localhost:8080")

    private val apiScenario = scenario("Rpc").repeat(3).on(exec(api).pause(2))

    init {
        setUp(
            apiScenario.injectOpen(CoreDsl.atOnceUsers(users)),
        ).protocols(httpProtocol)
    }
}
