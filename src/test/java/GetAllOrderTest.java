import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertFalse;

public class GetAllOrderTest {

    @Test
    @DisplayName("Check that a list of all orders can be got")
    public void checkAllOrderList(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.getAll();
        List<Object> orders = response.extract().jsonPath().getList("orders");
        assertFalse(orders.isEmpty());
    }

}
