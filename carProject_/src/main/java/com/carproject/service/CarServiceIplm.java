package com.carproject.service;

import com.carproject.model.Car;
import com.carproject.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceIplm implements CarService {

    private final CarRepository carRepository;
    private static final Logger logger = LoggerFactory.getLogger(CarServiceIplm.class);

    @Autowired
    public CarServiceIplm(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    @Override
    public Car addCar(Car car) {
        try {
            Car savedCar = carRepository.save(car);
            logger.info("Car added successfully: {}", savedCar);
            return savedCar;
        } catch (Exception e) {
            logger.error("Error adding car: ", e);
            throw new RuntimeException("Error adding car", e);
        }
    }
    @Override
    public List<Car> getAllCars() {
        try {
            List<Car> cars = carRepository.findAll();
            logger.info("Fetched all cars successfully.");
            return cars;
        } catch (Exception e) {
            logger.error("Error fetching cars: ", e);
            throw new RuntimeException("Error fetching cars", e);
        }
    }
    @Override
    public Optional<Car> getCarById(Long id) {
        try {
            Optional<Car> car = carRepository.findById(id);
            if (car.isPresent()) {
                logger.info("Car found with id: {}", id);
            } else {
                logger.warn("Car not found with id: {}", id);
            }
            return car;
        } catch (Exception e) {
            logger.error("Error fetching car by id: ", e);
            throw new RuntimeException("Error fetching car by id", e);
        }
    }
    @Override
    public Car updateCar(Long id, Car carDetails) {
        try {
            Car car = carRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Car not found with id " + id));
            car.setModel(carDetails.getModel());
            car.setMake(carDetails.getMake());
            car.setPreview(carDetails.getPreview());
            car.setDescription(carDetails.getDescription());
            car.setPrice(carDetails.getPrice());
            Car updatedCar = carRepository.save(car);
            logger.info("Car updated successfully: {}", updatedCar);
            return updatedCar;
        } catch (Exception e) {
            logger.error("Error updating car: ", e);
            throw new RuntimeException("Error updating car", e);
        }
    }
    @Override
    public void deleteCar(Long id) {
        try {
            Car car = carRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Car not found with id " + id));
            carRepository.delete(car);
            logger.info("Car deleted successfully with id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting car: ", e);
            throw new RuntimeException("Error deleting car", e);
        }
    }
}