import client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static model.CourierGenerator.getRandomCourier;
import static model.CourierGenerator.getRandomCourierWithObligatoryField;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class CourierCreationTest {

    private CourierClient courierClient;
    private Courier courier;
    private CourierLogin courierLogin;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check that a courier can be created")
    public void checkCourierCanBeCreated() {
        courier = getRandomCourier();
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        ValidatableResponse responseLogin = courierClient.login(courierLogin);
        courierId = responseLogin.extract().path("id");
        assertThat("Status code is not 201", statusCode, equalTo(201));
        assertTrue("Courier is not created", isCourierCreated);
        assertThat("Courier Id is not NULL", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Check that a courier can be created only with the obligatory fields")
    public void checkCourierCanBeCreatedWithObligatoryField() {
        courier = getRandomCourierWithObligatoryField();
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        ValidatableResponse responseLogin = courierClient.login(courierLogin);
        courierId = responseLogin.extract().path("id");
        assertThat("Status code is not 201", statusCode, equalTo(201));
        assertTrue("Courier is not created", isCourierCreated);
        assertThat("Courier Id is NULL", courierId, is(not(0)));

    }

    @Test
    @DisplayName("Check that a duplicated courier cannot be created")
    public void checkDuplicatedCourierCanNotBeCreated() {
        courier = getRandomCourier();
        courierClient.create(courier);
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        ValidatableResponse responseLogin = courierClient.login(courierLogin);
        courierId = responseLogin.extract().path("id");
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        assertThat("Status code is not 409", statusCode, equalTo(409));
        assertThat("Incorrect error message", errorMessage, equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
