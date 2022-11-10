package com.tcsswiggy.app;

public class Wallet {
    int balance;

    Wallet(int balance)
    {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    boolean deductPayment(int amnt)
    {
        if( balance > amnt)
        {
            balance -= amnt;
            return true;
        }
        else
        {
            return false;
        }
    }

    void updataBalance(int amnt)
    {
        balance += amnt;
    }

}
