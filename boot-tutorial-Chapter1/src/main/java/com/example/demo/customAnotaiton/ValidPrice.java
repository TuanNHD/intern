package com.example.demo.customAnotaiton;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPrice implements ConstraintValidator<ValidCarPrice, Double> {

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		
		return value!=null && value<100l;
	}

}
