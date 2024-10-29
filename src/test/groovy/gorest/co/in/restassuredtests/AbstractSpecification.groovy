package gorest.co.in.restassuredtests

import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.path.json.JsonPath
import io.restassured.specification.RequestSpecification
import io.restassured.http.*
import org.apache.http.HttpHeaders;
import spock.lang.Shared
import spock.lang.Specification

import static io.restassured.RestAssured.given

class AbstractSpecification extends Specification {
    @Shared
    protected static final BASE_URI = "https://gorest.co.in"
    protected static final TOKEN = "504b6ecbb400da620ab0e2e866332f66b1ee1d56aa722b1a81fb1cc2799eb613"

    @Shared
    JsonPath johnDoeUser = new JsonPath(getClass().getResource('/users/JohnDoe.json'))

    @Shared
    final RequestSpecification goRestRequestSpec = buildJsonSpec(BASE_URI)

    def buildJsonSpec(String baseUri){
        buildSpec(baseUri, ContentType.JSON)
    }

    def buildSpec(){
        buildSpec(new String(), ContentType.JSON)
    }

    def buildSpec(String baseUri, ContentType contentType){
        new RequestSpecBuilder()
        .setBaseUri(baseUri)
        .setContentType(contentType)
        .build()
    }

    def baseSpec(RequestSpecification requestSpec, String path) {
        given(requestSpec)
        .basePath(path)
    }

    def baseSpecWithToken(RequestSpecification requestSpec, String accessToken) {
        given(requestSpec)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
                .header(HttpHeaders.ACCEPT, 'application/json')
    }

    def setupSpec() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.useRelaxedHTTPSValidation()
    }
}
