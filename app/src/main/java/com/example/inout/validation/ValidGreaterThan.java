package com.example.inout.validation;

public class ValidGreaterThan extends  Validator{
    public ValidGreaterThan(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean validate(Object... params) {
        return ((double) params[1]) > ((double) params[2]);
    }
}
