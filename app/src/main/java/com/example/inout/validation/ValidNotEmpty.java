package com.example.inout.validation;

public class ValidNotEmpty extends Validator{

    public ValidNotEmpty(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean validate(Object... params) {
        return !((String)params[0]).isEmpty();
    }
}
