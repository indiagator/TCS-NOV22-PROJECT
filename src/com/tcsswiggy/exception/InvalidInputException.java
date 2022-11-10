package com.tcsswiggy.exception;

public class InvalidInputException extends Exception
{
    @Override
    public String getMessage()
    {
        return "The User entered an Invalid Input";
    }
}
