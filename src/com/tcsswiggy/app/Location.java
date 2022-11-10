package com.tcsswiggy.app;

public class Location
{
    String id;
    private float x; // instance variables
    float y;

    public Location(String id,float x, float y) // parameters
    {
        this.id = id;
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

    public String getId() {
        return id;
    }
}
