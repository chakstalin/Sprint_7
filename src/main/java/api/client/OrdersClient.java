package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;


import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class OrdersClient {

    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step("Create a new order")
    public Response createOrder(Object body) {
        return given()
                .baseUri(baseURI)
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Get list of orders")
    public Response getOrderList() {
        return given()
                .baseUri(baseURI)
                .when()
                .get(ORDER_PATH);
    }

}