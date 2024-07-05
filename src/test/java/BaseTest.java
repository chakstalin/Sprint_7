import api.client.*;
import api.POJO.*;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;


public abstract class BaseTest {

    protected CourierPOJO courier;
    protected boolean shouldDeleteData;
    protected CourierClient courierClient;
    protected OrdersClient ordersClient;

    @Before
    public void setUpBase() {
        courierClient = new CourierClient();
        ordersClient = new OrdersClient();
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courier = new CourierPOJO("VAS4test_login", "VAS4test_password", "VAS4test_firstName");
        shouldDeleteData = true;
    }

    @After
    public void tearDownBase() {
        if (shouldDeleteData) {
            CourierClient.deleteCourier(courier);
        }
    }
}


