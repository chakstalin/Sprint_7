import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest extends BaseTest {

    @Test
    @DisplayName("Get list of orders")
    public void testGetOrderListCode200andList() {
        shouldDeleteData = false;
        ordersClient.getOrderList()
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
