package pojo;

import java.io.Serializable;
import java.util.Comparator;

public class order implements Serializable , Comparator<String> {

    private static final long serialVersionUID = 1L;

    private String cusName;
    private String TableNum;
    private String FoodName;
    private String BeverageName;
    private String orderId;
    private String orderStatus;

    public order() {
    }

    public order(String cusName, String tableNum, String foodName, String beverageName, String orderId, String orderStatus) {
        this.cusName = cusName;
        TableNum = tableNum;
        FoodName = foodName;
        BeverageName = beverageName;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }


    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getTableNum() {
        return TableNum;
    }

    public void setTableNum(String tableNum) {
        TableNum = tableNum;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getBeverageName() {
        return BeverageName;
    }

    public void setBeverageName(String beverageName) {
        BeverageName = beverageName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    @Override
    public String toString() {
        return "customerName='" + cusName + '\'' +
                ", | TableNo='" + TableNum + '\'' +
                ", | Food='" + FoodName + '\'' +
                ", | Beverage='" + BeverageName + '\'' +
                ", | orderId='" + orderId + '\'';
    }

    @Override
    public int compare(String o1, String o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        return o1.compareTo(o2);    }
}

