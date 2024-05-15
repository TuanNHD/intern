package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dtos.CarDto;
import com.example.demo.entities.Car;

@Mapper
public interface CarMapper {
	@Mapping(source = "id", target = "id")
	CarDto toDto(Car entity);

	List<CarDto> toDto(List<Car> entities);

	@Mapping(source = "id", target = "id")
	Car toEntity(CarDto dto);

}
