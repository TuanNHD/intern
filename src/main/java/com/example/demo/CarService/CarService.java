package com.example.demo.CarService;

import java.util.List;

import com.example.demo.entity.Car;
public interface CarService {

	/**
	 * Retrieve all cars in the catalog.
	 * @return all cars
	 */
	public List<Car> findAll();
	
	public List<Car> listAddCar();
	/**
	 * search cars according to keyword in model and make.
	 * @param keyword for search
	 * @return list of car that match the keyword
	 */
	public List<Car> search(String keyword,List<Car> list);
}
