package api.client;

import api.POJO.*;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CourierClient {

    private static final String COURIER_PATH = "/api/v1/courier/";
    private static final String COURIER_LOGIN_PATH = COURIER_PATH + "login/";
    private static Integer courierId;

    @Step("Create a new courier")
    public Response createCourier(Object body) {
        return given()
                .baseUri(baseURI)
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(COURIER_PATH);
    }

    @Step("Try to login as a courier")
    public static Response loginCourier(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(COURIER_LOGIN_PATH);
    }

    @Step("Try to login as a courier and get Id")
    public static Integer loginCourierAndGetCourierId(Object body) {
        Response response = loginCourier(body);
        response.then().statusCode(200).body("id", notNullValue());
        return response.then().extract().path("id");
    }

    @Step("Try to login as a courier and delete that courier by ID")
    public static void deleteCourier(CourierPOJO body) {
        courierId = loginCourierAndGetCourierId(body);

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(COURIER_PATH + courierId).then().statusCode(200);
    }
}
