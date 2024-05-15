package com.carproject.service;

import java.util.List;
import java.util.Optional;

import com.carproject.model.Car;

public interface CarService {
	Car addCar(Car car);
	List<Car> getAllCars();
	Optional<Car> getCarById(Long id);
	Car updateCar(Long id, Car carDetails);
	void deleteCar(Long id);
}
