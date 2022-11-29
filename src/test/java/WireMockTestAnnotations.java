import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.given;

@WireMockTest
public class WireMockTestAnnotations {

    @Test
    @DisplayName("Annotation created mock server test")
    void annotationBasedTest(WireMockRuntimeInfo wmRuntimeInfo) {
        stubFor(get(urlEqualTo("/endpoint"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"greeting\":\"Well hello there!\"}")));
        int port = wmRuntimeInfo.getHttpPort();

        RestAssured.baseURI = String.format("http://localhost:%s", wmRuntimeInfo.getHttpPort());

        var body = given()
                .log().all()
                .get("/endpoint")
                .getBody();

        System.out.println("Response Body is: " + body.asString());
    }
}

