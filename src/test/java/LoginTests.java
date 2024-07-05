import api.POJO.CourierPOJO;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests extends BaseTest {

    @Before
    public void setUp() {
        courierClient.createCourier(courier);
    }

    @Test
    @DisplayName("Login as a courier with correct creds")
    public void testCourierLoginCode200() {
        courierClient.loginCourier(courier)
        .then().statusCode(200).body("id", notNullValue());
    }

    @Test
    @DisplayName("Login as a courier with missing password field")
    public void testLoginMissingPasswordFieldCode400() {
        Map<String, String> loginCourierData = new HashMap<>();
        loginCourierData.put("login", courier.getLogin());

         courierClient.loginCourier(loginCourierData)
                 .then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Can't login as a courier with missing login field")
    public void testLoginMissingLoginFieldCode400() {
        Map<String, String> passwrodCourierData = new HashMap<>();
        passwrodCourierData.put("password", courier.getPassword());

        courierClient.loginCourier(passwrodCourierData)
        .then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Can't login as a courier with wrong password")
    public void testLoginWrongPasswordCode404() {
        CourierPOJO wrongPasswordCourier = new CourierPOJO(courier.getLogin(),"VAS123wrong_password123");

        courierClient.loginCourier(wrongPasswordCourier)
        .then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Can't login as a courier with wrong login")
    public void testLoginWrongLoginCode404() {
        CourierPOJO wrongLoginCourier = new CourierPOJO("VAS123wrong_login123", courier.getPassword());

        courierClient.loginCourier(wrongLoginCourier)
                .then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Can't login with wrong credentials")
    public void testLoginWrongCredentialsCode404() {
        CourierPOJO wrongCredsCourier = new CourierPOJO("VAS123wrong_login123","VAS123wrong_password123");

        courierClient.loginCourier(wrongCredsCourier)
                .then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }
}
