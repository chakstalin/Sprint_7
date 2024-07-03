import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest extends BaseTest {


    @Test
    @Description("Create a new courier")
    public void testCreateCourierCode201() {
        Response response = createCourier(courier);
        response.then().statusCode(201).body("ok", equalTo(true));
    }

    @Test
    @Description("Try to create same courier")
    public void testCreateDuplicateCourierCode409() {
        createCourier(courier);
        Response response = createCourier(courier);
        response.then().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Description("Try to create a courier with the same login")
    public void testCreateSameLoginCourierCode409() {
        shouldDeleteData = false;
        createCourier(courier);
        courier.setPassword("VAS3333test_password");
        courier.setFirstName("VAS3333test_firstName");

        Response response = createCourier(courier);
        if (response.statusCode() == 201){
            shouldDeleteData = true;
        }
        response.then().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Description("Create a courier without login field")
    public void testCreateCourierWithoutLoginFieldCode400() {
        shouldDeleteData = false;
        Map<String, String> incompleteCourier = new HashMap<>();
        incompleteCourier.put("password", courier.getPassword());
        incompleteCourier.put("firstName", courier.getFirstName());

        Response response = given()
                .contentType(ContentType.JSON)
                .body(incompleteCourier)
                .when()
                .post("/api/v1/courier");
        if (response.statusCode() == 201){
            shouldDeleteData = true;
        }
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @Description("Create a courier without a password field")
    public void testCreateCourierWithoutPasswordFieldCode400() {
        shouldDeleteData = false;
        Map<String, String> incompleteCourier = new HashMap<>();
        incompleteCourier.put("login", courier.getLogin());
        incompleteCourier.put("firstName", courier.getFirstName());
        Response response = given()
                .contentType(ContentType.JSON)
                .body(incompleteCourier)
                .when()
                .post("/api/v1/courier");
        if (response.statusCode() == 201){
            shouldDeleteData = true;
        }
                response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
}
