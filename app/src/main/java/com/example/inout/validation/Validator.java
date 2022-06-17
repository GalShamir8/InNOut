package com.example.inout.validation;

public abstract class Validator {
    private String errorMessage = "";

    public Validator(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    abstract public boolean validate(Object ... params);
}
