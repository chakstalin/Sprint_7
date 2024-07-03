import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {

    private final List<String> colors;
    private Integer orderId;

    public CreateOrderTest(List<String> colors) {
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

    @After
    public void tearDown() {
        finishOrder(orderId);
    }

    @Test
    @Description("Create an order with colors")
    public void testCreateOrderHasTrackCode201() {
        shouldDeleteData = false;
        OrderPOJO order = generateRandomOrder();

        orderId = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .body("track", notNullValue())
                .extract()
                .path("track");
        System.out.println(colors);
    }

    @Step("Delete created order")
    private void finishOrder(Integer orderId) {
        if (orderId != null) {
           given()
                    .when()
                    .put("/api/v1/orders/finish/" + orderId);
        }
    }
}
