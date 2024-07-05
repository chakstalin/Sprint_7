import api.POJO.OrderPOJO;
import api.datagenerator.OrderDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest extends BaseTest {

    private final List<String> colors;

    public CreateOrderParameterizedTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {emptyList()}
        };
    }

    @Test
    @DisplayName("Create an order and get a track code")
    public void testCreateOrderHasTrackCode201() {
        shouldDeleteData = false;
        OrderPOJO order = OrderDataGenerator.generateRandomOrder();
        order.setColor(colors);

        ordersClient.createOrder(order)
        .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

}
