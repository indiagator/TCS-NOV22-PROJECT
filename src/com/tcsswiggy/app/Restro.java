package com.tcsswiggy.app;

import java.util.ArrayList;
import java.util.List;

public class Restro {

    String restroId;
    String restroname;
    Location location;

    List<Dish> menu;

    Restro(String restroId, String restroname)
    {
        this.restroId = restroId;
        this.restroname = restroname;
        this.menu = new ArrayList<>();
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getRestroname() {
        return restroname;
    }

    public List<Dish> getMenu() {
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
