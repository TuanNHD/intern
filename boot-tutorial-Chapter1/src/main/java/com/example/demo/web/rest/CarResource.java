/**
 * 
 */
package com.example.demo.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.CarDto;
import com.example.demo.entities.Car;
import com.example.demo.exception.ExistingCarException;
import com.example.demo.mapper.CarMapper;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;
import com.example.demo.validation.UserValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * @created Mar 26, 2023
 *
 *          TODO Tìm hiểu về REST API, RESTful API - Sự khác nhau giữa
 *          RestController và Controller trong spring
 */
@RestController
@RequestMapping("/api/car")
public class CarResource {
	@Autowired
	CarMapper carMapper;
	@Autowired
	UserValidator userValidator;
	@Autowired
	private CarService service;

	@Operation(summary = "get all car", description = "returns all car",tags= {"car"})
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Car.class))),
    		@ApiResponse(responseCode = "400", description = "system errors", content = @Content),
			@ApiResponse(responseCode = "404", description = "system errors", content = @Content) })
    @GetMapping
    public ResponseEntity<Page<Car>> getAllCars(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy){
        Pageable pageAble = PageRequest.of(page, size,Sort.by(sortBy).descending()); 
        return ResponseEntity.ok(service.getAllCars(pageAble));
    }

	@Operation(summary = "Find car by ID", description = "Returns a single car", tags = { "car" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Car.class))),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Car not found", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<CarDto> getCarById(@PathVariable(name = "id", required = true) Long id) {
		CarDto dto = service.getCarById(id);
		if (dto == null) {
			return new ResponseEntity<CarDto>(HttpStatusCode.valueOf(404));
		} else {
			return ResponseEntity.ok(dto);
		}
	}

	@Operation(summary = "save a car", description = "Returns status after save a car", tags = { "car" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CarDto.class))),
			@ApiResponse(responseCode = "404", description = "Car error", content = @Content) })
	@PostMapping
	public ResponseEntity<?> createCar(@Valid @RequestBody CarDto dto,BindingResult result) {
		
		userValidator.validate(dto, result);
		System.out.println(result.getAllErrors());
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		return ResponseEntity.ok(service.createNewCar(dto));
	}

	@Operation(summary = "update a car by ID", description = "Returns a single car after update", tags = { "car" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "update successful ", content = @Content(schema = @Schema(implementation = CarDto.class))),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Car not found", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCar(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody CarDto dto,BindingResult bindingResult) throws ExistingCarException {
		dto.setId(id);
		userValidator.validate(dto, bindingResult);
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
			}
		boolean success = service.updateExistingCar(dto);
		if (success) {
			return ResponseEntity.ok(dto);
		} else {
			throw new ExistingCarException();
		}
	}

	@Operation(summary = "Delete car by ID", description = "Returns status", tags = { "car" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = " delete successfully ", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeCar(@PathVariable(name = "id", required = true) Long id) {
		boolean success = service.removeExistingCar(id);
		if (success) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@Autowired
	private CarRepository carRepository;
	@GetMapping("/ats")
	public void ats() {
		carRepository.autoSaveCar();
	}
}
