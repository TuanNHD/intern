package com.example.demo.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.dtos.CarDto;
import com.example.demo.entities.Car;
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Car.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CarDto car = (CarDto) target;

		if (car.getPrice() < 20) {
			errors.rejectValue("price", "price.invalid", "price must be at least 18");
		}
	}



}
