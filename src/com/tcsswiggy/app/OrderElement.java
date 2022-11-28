package com.tcsswiggy.app;

import java.io.Serializable;

public class OrderElement implements Serializable {

    Dish dish;
    int qty;

    OrderElement(Dish dish, int qty)
    {
        this.dish = dish;
        this.qty = qty;
    }

    public Dish getDish() {
        return dish;
    }

    public int getQty() {
        return qty;
    }
}
