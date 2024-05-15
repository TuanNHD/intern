package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.demo.entities.Car;
@EnableJpaRepositories
public   interface CarRepo extends JpaRepository<Car, Long> {

}
