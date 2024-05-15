/**
 * 
 */
package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entities.Account;
import com.example.demo.entities.Car;

/**
 * @created Mar 26, 2023
 *
 */
public interface CarRepository {

    Page<Car> findAll(Pageable pageable);

    Car findById(Long id);

    void save(Car entity);

    void delete(Car entity);
    
    void save();
    void updateCar(Car enCar);
    Account getAccByUser(Account acc);
    void autoSaveCar();

}
