package com.example.inout.validation;

public class ValidLessThan extends Validator{
    public ValidLessThan(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean validate(Object... params) {
        return ((double) params[1]) < ((double) params[2]);

    }
}
