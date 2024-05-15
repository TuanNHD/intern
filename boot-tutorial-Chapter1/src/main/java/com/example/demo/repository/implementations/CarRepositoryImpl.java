/**
 * 
 */
package com.example.demo.repository.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Account;
import com.example.demo.entities.Car;
import com.example.demo.repository.CarRepo;
import com.example.demo.repository.CarRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * @created Mar 26, 2023
 *
 *          TODO use JPARepository Đối với repository layout có nhiều cách triển
 *          khai khác nhau Tìm hiểu và áp dụng Spring data jpa
 * 
 */
@Repository
public class CarRepositoryImpl implements CarRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	CarRepo carRepo;
	static int i = 1;
	@Autowired
	PasswordEncoder encoder;

	@Override
	public Page<Car> findAll(Pageable pageable) {

//		String query = "SELECT * FROM car";
//
//		// FIXME Unsafe type
//		List<Car> result = entityManager.createNativeQuery(query, Car.class).getResultList();

		return carRepo.findAll(pageable);
		
	}

	@Override
	public Car findById(Long id) {
		return entityManager.find(Car.class, id);
	}

	@Override
	@Transactional
	/*
	 * TODO Tìm hiểu về transaction trong sql, hibernate và spring data jpa
	 */
	public void save(Car entity) {
		entityManager.persist(entity);
	}
	@Transactional
    public void updateCar(Car entity) {
//        String sql = "UPDATE car SET name = :name, description = :description, price = :price WHERE id = :id";
//        
//        entityManager.createNativeQuery(sql)
//            .setParameter("name", entity.getName())
//            .setParameter("description", entity.getDescription())
//            .setParameter("price", entity.getPrice())
//            .setParameter("id", entity.getId())
//            .executeUpdate();
		entityManager.merge(entity);
    }
	@Override
	@Transactional
	public void save() {
		Account entity = new Account();
		entity.setUsername("tuannhd" + String.valueOf(i));
		entity.setPassword(encoder.encode("123"));
		entityManager.persist(entity);
		i += 1;
	}

	@Override
	@Transactional
	public Account getAccByUser(Account acc) {
		return entityManager.find(Account.class, acc.getUsername());
	}

	@Override
	@Transactional
	public void delete(Car entity) {
		entityManager.remove(entity);
	}
	@Override
	@Transactional
	public void autoSaveCar() {
		while (i <= 50) {
			Car entity = new Car();
			entity.setName("tuannhd" + String.valueOf(i));
			entity.setDescription("hihi" + String.valueOf(i));
			entity.setPrice(12 + i);
			entityManager.persist(entity);
			i += 1;
		}
		;
	}

}
