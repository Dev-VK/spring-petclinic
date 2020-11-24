package performancetests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class SubmitForm extends Simulation {

  // specify the URL to test
  val devUrl = "http://127.0.0.1:8081"

  // specify the protocol
  val httpProtocol = http.baseUrl(devUrl)

  // define the scenario
  val scn = scenario("Get Owner")
        .exec(http("request_0")
            .get("/owners?lastName=Jenkins"))
        .pause(5)

  // define users and execute the scenario
  setUp(scn.inject(
      atOnceUsers(1)
      )
    .protocols(httpProtocol))

}
