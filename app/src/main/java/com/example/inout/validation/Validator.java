package com.example.inout.validation;

public abstract class Validator {
    private String errorMessage = "";
    private Rule validateRule = Rule.NOT_EMPTY;

    abstract public boolean validate(Object ... params);
}
