package com.tcsswiggy.app;

public class Restro {

    String restroId;
    String restroname;
    Location location;

    Dish[] menu;

    Restro(String restroId, String restroname)
    {
        this.restroId = restroId;
        this.restroname = restroname;
        this.menu = new Dish[10];
    }

    public void setMenu(Dish[] menu) {
        this.menu = menu;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getRestroname() {
        return restroname;
    }

    public Dish[] getMenu() {
        return menu;
    }

    public Location getLocation() {
        return location;
    }

    public String getRestroId() {
        return restroId;
    }

    @Override
    public String toString() {
        return "Restro{" +
                "restroname='" + restroname + '\'' +
                '}';
    }
}
