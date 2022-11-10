package com.tcsswiggy.app;

public class OrderElement {

    String dishId;
    int qty;

    OrderElement(String dishId, int qty)
    {
        this.dishId = dishId;
        this.qty = qty;
    }

    public String getDishId() {
        return dishId;
    }

    public int getQty() {
        return qty;
    }
}
