package api.datagenerator;

import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

public class CourierDataGenerator {

    @Step("Create incomplete courier data without login")
    public static Map<String, String> createCourierDataWithoutLogin(String password, String firstName) {
        Map<String, String> incompleteCourier = new HashMap<>();
        incompleteCourier.put("password", password);
        incompleteCourier.put("firstName", firstName);
        return incompleteCourier;
    }

    @Step("Create incomplete courier data without password")
    public static Map<String, String> createCourierDataWithoutPassword(String login, String firstName) {
        Map<String, String> incompleteCourier = new HashMap<>();
        incompleteCourier.put("login", login);
        incompleteCourier.put("firstName", firstName);
        return incompleteCourier;
    }
}
