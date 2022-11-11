package com.tcsswiggy.app;

public class Dish { // BluePrint for Dish Entity

    String restroId;
    String dishId;
    String dishName;
    int price;
    Review[] reviews;
    String description;
    int qty; // in grams
    boolean vegNveg; // true - nveg | false - veg
    String type;
    String[] tags; // bestseller, spicy, cooks fast...

    Dish(String restroId, String dishId, String dishName, int price)
    {
        this.restroId = restroId;
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;

        reviews = new Review[10];
        tags = new String[5];
    }




    public int getPrice() {
        return price;
    }

    public String getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public String getRestroId() {
        return restroId;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public int getQty() {
        return qty;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String[] getTags() {
        return tags;
    }

    @Override
    public String toString()
    {
        return dishName+" "+price;
    }

}
