package api.POJO;

import java.util.List;

public class OrderPOJO {

    public void setColor(List<String> color) {
        this.color = color;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public OrderPOJO(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    public OrderPOJO(){
    }

}