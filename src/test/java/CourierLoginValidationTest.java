import client.CourierClient;
import io.restassured.response.ValidatableResponse;
import model.CourierGenerator;
import model.CourierLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)

public class CourierLoginValidationTest {

    private final String login;
    private final String password;

    public CourierLoginValidationTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {CourierGenerator.getCourierLogin(), ""},
                {"", CourierGenerator.getCourierPassword()},
                {"", ""},
                {CourierGenerator.getCourierLogin(), null},
                {null, CourierGenerator.getCourierPassword()},
                {null, null}
        };
    }
    @Test
    public void checkInvalidRequestIsNotAllowed(){

        CourierClient courierClient = new CourierClient();
        CourierLogin courierLogin = new CourierLogin(login, password);
        ValidatableResponse response = courierClient.login(courierLogin);
        int code = response.extract().statusCode();
        assertThat("Status code is not 400", code, equalTo(400));
        String errorMessage = response.extract().path("message");
        assertThat("Incorrect error message", errorMessage, equalTo("Недостаточно данных для входа"));
    }
}
