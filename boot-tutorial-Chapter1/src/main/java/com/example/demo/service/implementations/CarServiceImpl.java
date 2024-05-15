/**
 * 
 */
package com.example.demo.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.CarDto;
import com.example.demo.entities.Account;
import com.example.demo.entities.Car;
import com.example.demo.mapper.CarMapper;
import com.example.demo.repository.CarRepo;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;

/**
 * @created Mar 26, 2023
 *
 */
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository repository;
    @Autowired
    CarRepo carRepo;

    @Autowired
    private CarMapper mapper;

    @Override
    public Page<Car> getAllCars(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public CarDto getCarById(Long id) {

        Car car = repository.findById(id);

        return mapper.toDto(car);
    }

    @Override
    public CarDto createNewCar(CarDto dto) {

        Car newCar = mapper.toEntity(dto);

        repository.updateCar(newCar);

        return mapper.toDto(newCar);
    }
    public void saveAcc() {
    	
        repository.save();;

    }

    @Override
    public boolean updateExistingCar(CarDto dto) {

        Long carId = dto.getId();

        Car existingCar = repository.findById(carId);

        if (existingCar == null) {
            return false;
        }

        existingCar = mapper.toEntity(dto);

        repository.updateCar(existingCar);

        return true;
    }

    @Override
    public boolean removeExistingCar(Long id) {

        Car existingCar = repository.findById(id);

        if (existingCar == null) {
            return false;
        }

        repository.delete(existingCar);

        return true;
    }

	@Override
	public void save() {
		repository.save();
	}
	@Override
	public Account getAccByUser(Account acc){
		return repository.getAccByUser(acc);
	}
}
