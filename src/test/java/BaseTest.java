import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public abstract class BaseTest {

    protected CourierPOJO courier;
    protected Integer courierId;
    protected boolean shouldDeleteData;
    protected Random random = new Random();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courier = new CourierPOJO("VAS4test_login", "VAS4test_password", "VAS4test_firstName");
        shouldDeleteData = true;
    }

    @After
    public void tearDown() {
        if (shouldDeleteData) {
            deleteCourier();
            System.out.println("Udalil dannie");
        }
    }

    @Step("Send request to create a courier")
    protected Response createCourier(CourierPOJO courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Delete a courier by ID")
    protected void deleteCourier() {
        courierId = loginCourier(courier);
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/courier/" + courierId).then().statusCode(200);
    }

    @Step("Login as courier")
    protected Integer loginCourier(CourierPOJO courier) {
        Map<String, String> loginCourierData = new HashMap<>();
        loginCourierData.put("login", courier.getLogin());
        loginCourierData.put("password", courier.getPassword());
        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginCourierData)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(200).body("id", notNullValue());;
        return response.then().extract().path("id");
    }

   @Step("Generate random order")
    protected OrderPOJO generateRandomOrder() {
        return new OrderPOJO(
                "firstName" + random.nextInt(1000),
                "lastName" + random.nextInt(1000),
                "address" + random.nextInt(1000),
                "metro" + random.nextInt(10),
                "phone" + random.nextInt(1000),
                "1" + random.nextInt(10),
                "2024-07-15",
                "comment" + random.nextInt(1000),
                Collections.emptyList()
        );
    }
}

