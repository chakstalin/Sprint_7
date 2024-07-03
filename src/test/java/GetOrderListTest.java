import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest extends BaseTest {

    @Test
    @Description("Get list of orders")
    public void testGetOrderListCode200andList() {
        shouldDeleteData = false;
        given()
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}

