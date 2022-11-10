package com.tcsswiggy.app;

public class Customer {

    String username;
    String phonenumber;
    String email;
    Location location;
    Wallet wallet;

    public Customer(String username, Location location)
    {
        this.username = username;
        this.location = location;
        this.wallet = new Wallet(1000);
    }

    public String getUsername() {
        return username;
    }

    public Location getLocation() {
        return location;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
