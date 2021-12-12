import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static model.OrderGenerator.getRandomOrder;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)

public class OrderCreateTest {

    private final String[] color;

    public OrderCreateTest(String[] color){
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "BLACK"}},
                {new String[]{"", ""}},
        };
    }

    @Test
    @DisplayName("Check that orders with different colors can be created")
    public void checkOrderCreateTest(){
        Order order = getRandomOrder().setColor(color);
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.create(order);

        int statusCode = response.extract().statusCode();
        int trackNumber = response.extract().path("track");

        assertThat("Status Code is not 201", statusCode, equalTo(201));
        assertThat("Track Number is NULL", trackNumber, is(not(0)));
    }
}
