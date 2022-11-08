package com.tcsswiggy.app;

public class Location
{
    //GPS representation
    public static String locationType = "xy";
    private float x; // instance variables
    float y;

    public Location(float x, float y) // parameters
    {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
