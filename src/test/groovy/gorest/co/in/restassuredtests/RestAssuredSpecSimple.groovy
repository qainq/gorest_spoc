package gorest.co.in.restassuredtests

import static io.restassured.RestAssured.*
import static org.hamcrest.Matchers.equalTo


import io.restassured.builder.RequestSpecBuilder
import spock.lang.Shared
import spock.lang.Specification

class RestAssuredSpecSimple extends Specification {
    @Shared
    def requestSpec =
            new RequestSpecBuilder()
                    .setBaseUri("https://jsonplaceholder.typicode.com")
                    .build()

    def "validate json response"() {
        given: "set up request"
        def request = given(requestSpec)

        when: "get todos"
        def response = request.get("/users/1")


        then: "should 200 okay, response matching expected"
        response.then()
                .statusCode(200)
                .body('id', equalTo(1))
                .body("name", equalTo("Leanne Graham"))
                .body("company.name", equalTo("Romaguera-Crona"))
    }
}
