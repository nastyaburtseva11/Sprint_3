import client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CourierCreateValidationTest {

    private final Courier courier;

    public CourierCreateValidationTest(Courier courier){
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {CourierGenerator.getRandomCourierWithLoginOnly()},
                {CourierGenerator.getRandomCourierWithPasswordOnly()},
                {CourierGenerator.getRandomCourierWithFirstNameOnly()}
        };
    }

    @Test
    @DisplayName("Check that a courier without login and password cannot be created")
    public void checkInvalidRequestIsNotAllowed(){
        ValidatableResponse response = new CourierClient().create(courier);

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Status Code is not 400", statusCode, equalTo(400));
        assertThat("Error message is incorrect", errorMessage, equalTo("Недостаточно данных для создания учетной записи"));
    }

}
