import client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierLogin;
import org.junit.Test;

import static model.CourierGenerator.getRandomCourier;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierLoginTest {

    private CourierClient courierClient;
    private Courier courier;
    private CourierLogin courierLogin;

    @Test
    @DisplayName("Check that a courier can login with valid password and login request returns courier ID")
    public void checkCourierLogin(){
        courier = getRandomCourier();
        courierClient = new CourierClient();
        courierClient.create(courier);
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        ValidatableResponse response = courierClient.login(courierLogin);
        int code = response.extract().statusCode();
        int courierId = response.extract().path("id");
        assertThat("Status code is not 200", code, equalTo(200));
        assertThat("Courier Id is NULL", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Check that a nonexistent courier cannot login")
    public void checkCourierNotExistLogin(){
        courier = getRandomCourier();
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        courierClient = new CourierClient();
        ValidatableResponse response = courierClient.login(courierLogin);
        int code = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        assertThat("Status code is not 404", code, equalTo(404));
        assertThat("Incorrect error message", errorMessage, equalTo("Учетная запись не найдена"));

    }
}


