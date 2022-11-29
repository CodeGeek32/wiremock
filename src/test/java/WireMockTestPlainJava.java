import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;

public class WireMockTestPlainJava {

    WireMockServer wms;

    @BeforeEach
    void setUp() {
        // start and set up instance of WireMock with an endpoint here
        wms = new WireMockServer(options().port(8089));
        //No-args constructor will start on port 8080, no HTTPS
        wms.start();

        var stubBuilder = get(urlEqualTo("/endpoint"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"greeting\":\"Well hello there!\"}"));
        wms.stubFor(stubBuilder);
    }

    @Test
    @DisplayName("Plain java mock server creation test")
    void run() {

        RestAssured.baseURI = String.format("http://localhost:%s", wms.port());

        var body = given()
                .log().all()
                .get("/endpoint")
                .getBody();

        System.out.println("Response Body is: " + body.asString());
    }

    @AfterEach
    void tearDown() {
        wms.stop();
    }
}
