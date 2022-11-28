package com.tcsswiggy.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restro restro = (Restro) o;

        if (!Objects.equals(restroId, restro.restroId)) return false;
        return Objects.equals(restroname, restro.restroname);
    }

    @Override
    public int hashCode() {
        int result = restroId != null ? restroId.hashCode() : 0;
        result = 31 * result + (restroname != null ? restroname.hashCode() : 0);
        return result;
    }
}
