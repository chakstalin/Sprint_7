import api.client.CourierClient;
import api.POJO.CourierPOJO;
import api.datagenerator.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends BaseTest {

    @Test
    @DisplayName("Create a new courier with correct data")
    public void testCreateCourierCode201() {
        Response response = courierClient.createCourier(courier);
        response.then().statusCode(201).body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Can't create same courier")
    public void testCreateDuplicateCourierCode409() {
        courierClient.createCourier(courier);
        Response response = courierClient.createCourier(courier);
        response.then().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Can't create a courier with the same login")
    public void testCreateSameLoginCourierCode409() {
        shouldDeleteData = false;
        courierClient.createCourier(courier);
        courier.setPassword("new_password");
        courier.setFirstName("new_firstName");

        Response response = courierClient.createCourier(courier);
        if (response.statusCode() == 201) {
            shouldDeleteData = true;
        }
        response.then().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Can't create a courier without login field")
    public void testCreateCourierWithoutLoginFieldCode400() {
        shouldDeleteData = false;
        Map<String, String> courierWithoutLogin =
                CourierDataGenerator
                .createCourierDataWithoutLogin(courier.getPassword(), courier.getFirstName());

        Response response = courierClient.createCourier(courierWithoutLogin);
        if (response.statusCode() == 201) {
            shouldDeleteData = true;
        }
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Can't create courier without a password field")
    public void testCreateCourierWithoutPasswordFieldCode400() {
        shouldDeleteData = false;
        Map<String, String> courierWithoutPassword =
                CourierDataGenerator
                        .createCourierDataWithoutPassword(courier.getLogin(), courier.getFirstName());

        Response response = courierClient.createCourier(courierWithoutPassword);
        if (response.statusCode() == 201) {
            shouldDeleteData = true;
        }
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
