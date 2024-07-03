import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests extends BaseTest {


    @Test
    @Description("Login as a courier")
    public void testCourierLoginCode200() {
        createCourier(courier);
        loginCourier(courier);
    }

    @Test
    @Description("Login with missing password field")
    public void testLoginMissingPasswordFieldCode400() {
        createCourier(courier);
        Map<String, String> loginCourierData = new HashMap<>();
        loginCourierData.put("login", courier.getLogin());

        given()
                .contentType(ContentType.JSON)
                .body(loginCourierData)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Login with missing login field")
    public void testLoginMissingLoginFieldCode400() {
        createCourier(courier);
        Map<String, String> loginCourierData = new HashMap<>();
        loginCourierData.put("password", courier.getPassword());

        given()
                .contentType(ContentType.JSON)
                .body(loginCourierData)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Login with wrong password")
    public void testLoginWrongPasswordCode404() {
        createCourier(courier);
        Map<String, String> loginCourierData = new HashMap<>();
        loginCourierData.put("login", courier.getLogin());
        loginCourierData.put("password", "wrong_password");

        given()
                .contentType(ContentType.JSON)
                .body(loginCourierData)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Login with wrong login or password")
    public void testLoginWrongLoginCode404() {
        createCourier(courier);
        Map<String, String> loginCourierData = new HashMap<>();
        loginCourierData.put("login", "wrong_login");
        loginCourierData.put("password", courier.getPassword());

        given()
                .contentType(ContentType.JSON)
                .body(loginCourierData)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Login with wrong creds")
    public void testLoginWrongCredentialsCode404() {
        createCourier(courier);
        Map<String, String> loginCourierData = new HashMap<>();
        loginCourierData.put("login", "wrong_111login111");
        loginCourierData.put("password", "wrong_111password111");

        given()
                .contentType(ContentType.JSON)
                .body(loginCourierData)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
