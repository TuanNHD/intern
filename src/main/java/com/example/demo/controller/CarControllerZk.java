package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Repository.CarRepository;

@Controller
public class CarControllerZk {
	@Autowired
	CarRepository carRepository;
	@RequestMapping(value = "/car")
	public String hello() {
		return "listCar";
	}
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}
