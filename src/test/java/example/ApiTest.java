package example;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.satschel.com/v2/aml/kyb";
    }

    private Response sendRequest() throws IOException {
        // Load JSON body from file
        String jsonBody = new String(Files.readAllBytes(Paths.get("src/test/resources/requestbody.json")));

        Response response = given()
                .queryParam("type", "standard")
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post();

        System.out.println("Response status code: " + response.statusCode());
        assertThat(response.statusCode(), equalTo(200));
        return response;
    }

    @Test
    public void validateResponseSchema() throws IOException {
        Response response = sendRequest();

        // Load JSON schema from file
        String schema = new String(Files.readAllBytes(Paths.get("src/test/resources/schema.json")));

        // Validate response against the schema if the response is successful
        if (response.statusCode() == 200) {
            response.then().assertThat().body(matchesJsonSchema(schema));
        } else {
            // Print the error response for debugging
            System.out.println("Error response: " + response.asString());
        }
    }

    @Test
    public void validateVerifiedInputFields() throws IOException {
        Response response = sendRequest();

        // Use ValidationUtils to validate response content if the response is successful
        if (response.statusCode() == 200) {
            ValidationUtils.validateVerifiedInput(response, "SATSCHEL", "30 AUGUSTA CANYON WAY",
                    "LAS VEGAS", "NV", "89141");
        }
    }

    @Test
    public void validateVerificationIndicatorsFields() throws IOException {
        Response response = sendRequest();

        // Use ValidationUtils to validate response content if the response is successful
        if (response.statusCode() == 200) {
            ValidationUtils.validateVerificationIndicators(response, "1", "1", "1", "1", "1", "0", "0");
        }
    }
}