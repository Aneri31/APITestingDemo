package example;

import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ValidationUtils {

    public static void validateVerifiedInput(Response response, String expectedCompanyName,
                                             String expectedStreetAddress1, String expectedCity,
                                             String expectedState, String expectedZip5) {
        String verifiedInputPath = "data.CompanyResults.VerifiedInput";
        assertThat(response.jsonPath().getString(verifiedInputPath + ".CompanyName"), equalTo(expectedCompanyName));
        assertThat(response.jsonPath().getString(verifiedInputPath + ".Address.StreetAddress1"), equalTo(expectedStreetAddress1));
        assertThat(response.jsonPath().getString(verifiedInputPath + ".Address.City"), equalTo(expectedCity));
        assertThat(response.jsonPath().getString(verifiedInputPath + ".Address.State"), equalTo(expectedState));
        assertThat(response.jsonPath().getString(verifiedInputPath + ".Address.Zip5"), equalTo(expectedZip5));
    }

    public static void validateVerificationIndicators(Response response, String expectedCompanyName,
                                                      String expectedStreetAddress, String expectedCity,
                                                      String expectedState, String expectedZip,
                                                      String expectedPhone, String expectedFEIN) {
        String verificationIndicatorsPath = "data.CompanyResults.VerificationIndicators";
        assertThat(response.jsonPath().getString(verificationIndicatorsPath + ".CompanyName"), equalTo(expectedCompanyName));
        assertThat(response.jsonPath().getString(verificationIndicatorsPath + ".StreetAddress"), equalTo(expectedStreetAddress));
        assertThat(response.jsonPath().getString(verificationIndicatorsPath + ".City"), equalTo(expectedCity));
        assertThat(response.jsonPath().getString(verificationIndicatorsPath + ".State"), equalTo(expectedState));
        assertThat(response.jsonPath().getString(verificationIndicatorsPath + ".Zip"), equalTo(expectedZip));
        assertThat(response.jsonPath().getString(verificationIndicatorsPath + ".Phone"), equalTo(expectedPhone));
        assertThat(response.jsonPath().getString(verificationIndicatorsPath + ".FEIN"), equalTo(expectedFEIN));
    }
}
