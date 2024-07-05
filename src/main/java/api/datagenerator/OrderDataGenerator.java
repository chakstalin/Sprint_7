package api.datagenerator;

import api.POJO.OrderPOJO;
import io.qameta.allure.Step;

import java.util.Collections;
import java.util.Random;

public class OrderDataGenerator {

    private static Random random = new Random();

    @Step("Generate random order")
    public static OrderPOJO generateRandomOrder() {
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
