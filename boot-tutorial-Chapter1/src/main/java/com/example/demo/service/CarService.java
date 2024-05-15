/**
 * 
 */
package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dtos.CarDto;
import com.example.demo.entities.Account;
import com.example.demo.entities.Car;

/**
 * @created Mar 26, 2023
 *
 */
public interface CarService {

    /**
     * @return all existing cars
     */
    Page<Car> getAllCars(Pageable pageable);

    /**
     * @param id the id of car
     * @return cardto
     */
    CarDto getCarById(Long id);

    /**
     * @param dto car data to create
     * @return cardto
     */
    CarDto createNewCar(CarDto dto);

    /**
     * @param dto car data to update
     * @return boolean success or failed
     */
    boolean updateExistingCar(CarDto dto);

    /**
     * @param id id of car to remove
     * @return boolean success or failed
     */
    boolean removeExistingCar(Long id);
    
    void save();
    
    Account getAccByUser(Account acc);
}
