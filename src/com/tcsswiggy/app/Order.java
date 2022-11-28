package com.tcsswiggy.app;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order implements Serializable {

    String orderId;
    String restroId;
    String customerId;
    Set<OrderElement> dishList;
    int orderAmnt=0;
    int interimOrderState;
    int finalOrderState; // 0 - cancelled | 1 - delivered

    Order(String orderId, String customerId, String restroId )
    {
        this.orderId = orderId;
        this.customerId = customerId;
        this.restroId = restroId;

        dishList = new HashSet<>();
    }

    void setOrderElement(OrderElement orderElement)
    {
        dishList.add(orderElement);
        orderAmnt += orderElement.getDish().getPrice()*orderElement.getQty();
    }

    void clearDishList()
    {
        dishList = null;
        orderAmnt=0;
    }

    public Set<OrderElement> getDishList() {
        return dishList;
    }

    public void setFinalOrderState(int finalOrderState) {
        this.finalOrderState = finalOrderState;
    }

    public String getRestroId() {
        return restroId;
    }

    public int getFinalOrderState() {
        return finalOrderState;
    }

    public int getInterimOrderState() {
        return interimOrderState;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getOrderAmnt() {
        return orderAmnt;
    }

    public String getOrderId() {
        return orderId;
    }
}
