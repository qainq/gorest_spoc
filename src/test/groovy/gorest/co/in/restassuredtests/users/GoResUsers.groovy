package gorest.co.in.restassuredtests.users

import gorest.co.in.restassuredtests.AbstractSpecification
import io.restassured.path.json.JsonPath
import org.apache.http.HttpStatus
import org.hamcrest.Matchers

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.iterableWithSize

class GoResUsers extends AbstractSpecification {

    JsonPath sMukhopadhyay = new JsonPath(getClass().getResource('/users/SMukhopadhyay.json'))

    def "Validate GET users size is 10"() {
        given: "Setup request"
        def request = baseSpecWithToken(goRestRequestSpec, TOKEN)
        request = baseSpec(request, "/public/v2/users")

        when:
        def response = request.when().get()

        then: "There are at least 4 users"
        def firstId = response.then()
                .statusCode(HttpStatus.SC_OK)
                .body('id', iterableWithSize(10))
                .body('id[1]', Matchers.isA(Integer.class))
    }

    def "Validate PATCH method is updated user "(int userId, String name) {
        given: "Setup request"
        def request = baseSpecWithToken(goRestRequestSpec, TOKEN)
        request = baseSpec(request, "/public/v2/users/" + userId)
        request = request.body(getsMukhopadhyay().prettify())

        when:
        request.when().patch().then()
                .statusCode(HttpStatus.SC_OK)

        then: "Validate if record is updated"
        request.when().get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(name))
                .body("gender", equalTo("male"))
                .body("status", equalTo("active"))


        where:
        userId    | name
        '7497678' | 'Subhashini Mukhopadhyay'
        '7494993' | 'Subhashini Mukhopadhyay'
    }

    def "Validate post request"() {
        given: "Setup request"
        def request = baseSpecWithToken(goRestRequestSpec, TOKEN)
        request = baseSpec(request, "/public/v2/users")

        request = request.body(johnDoeUser.prettify())

        when:
        def response = request.when().post()

        then: "There are at least 4 users"
        response.then()
                .statusCode(201)
    }

}