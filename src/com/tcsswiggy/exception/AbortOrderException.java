package com.tcsswiggy.exception;

public class AbortOrderException extends Exception {

    private String reason;

    public AbortOrderException(String reason)
    {
        this.reason = reason;
    }

    @Override
    public String toString()
    {
        return "Customer wants to Abort! :"+ reason;
    }

    public String getReason() {
        return reason;
    }
}
