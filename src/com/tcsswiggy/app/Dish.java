package com.tcsswiggy.app;

public class Dish { // BluePrint for Dish Entity

    String dishId;
    String dishName;
    int price;
    Review[] reviews;
    String description;
    int qty; // in grams
    boolean vegNveg; // true - nveg | false - veg
    String type;
    String[] tags; // bestseller, spicy, cooks fast...

    Dish(String dishId, String dishName, boolean vegNveg, String type)
    {
        this.dishId = dishId;
        this.dishName = dishName;
        this.vegNveg = vegNveg;
        this.type = type;

        reviews = new Review[10];
        tags = new String[5];
    }

}
