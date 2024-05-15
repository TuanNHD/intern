package com.example.demo.mapper.implementations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.mapper.CarMapper;
import com.example.demo.mapper.CarMapperImpl;
import com.example.demo.validation.UserValidator;

@Configuration
public class MapperConfig {
@Bean
public CarMapper beanMapper() {
	return new CarMapperImpl();
}
@Bean
public UserValidator createBeanValidator() {
	return new UserValidator();
}
}
