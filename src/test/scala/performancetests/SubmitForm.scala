package performancetests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class SubmitForm extends Simulation {

  // specify the URL to test
  val devUrl = "http://dev.timshort.liatr.io/personal-banking/"

  // specify the protocol
  val httpProtocol = http.baseUrl(devUrl)

  // define the scenario
  val scn = scenario("Submit a contact form")
    .exec(http("GetHomePage").get("/"))
    .pause(5)
    .exec(http("PostForm") // Submit form
      .post("#")
      .formParam("name", "John Adams")
      .formParam("email", "johnadams@test.liatr.io")
      .formParam("message", "I would like more information on your products")
      )

  // define users and execute the scenario
  setUp(scn.inject(
      atOnceUsers(1)
      )
    .protocols(httpProtocol)
    .assertions(forAll.failedRequests.percent.lte(0)))
}
