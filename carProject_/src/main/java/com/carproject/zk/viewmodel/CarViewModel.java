package com.carproject.zk.viewmodel;

import com.carproject.model.Car;
import com.carproject.service.CarService;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

public class CarViewModel {

    private List<Car> cars;
    private Car newCar = new Car();
    private Car selectedCar;

    @Autowired
    private CarService carService;

    @Init
    public void init() {
        cars = carService.getAllCars();
    }

    public List<Car> getCars() {
        return cars;
    }

    public Car getNewCar() {
        return newCar;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    @Command
    @NotifyChange({"cars", "newCar"})
    public void submit() {
        try {
            Car savedCar = carService.addCar(newCar);
            if (savedCar != null) {
                newCar = new Car(); // Reset the form
                cars = carService.getAllCars(); // Refresh the list
                Executions.sendRedirect("/");
                System.out.println("New car added successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error adding new car: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Command
    @NotifyChange("cars")
    public void save() {
        try {
            carService.updateCar(selectedCar.getId(), selectedCar);
            cars = carService.getAllCars();
            Executions.sendRedirect("home.zul");
            System.out.println("Car updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating car: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Command
    @NotifyChange("cars")
    public void delete(@BindingParam("carId") Long carId) {
        try {
            carService.deleteCar(carId);
            cars = carService.getAllCars();
            Messagebox.show("Car deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting car: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Command
    public void addNew() {
        Executions.sendRedirect("addCar.zul");
        System.out.println("Navigating to Add New Car form.");
    }

    @Command
    public void edit(@BindingParam("carId") Long carId) {
        try {
            Optional<Car> carOptional = carService.getCarById(carId);
            if (carOptional.isPresent()) {
                selectedCar = carOptional.get();
                Map<String, Object> args = new HashMap<>();
                args.put("carId", carId);
                Executions.createComponents("editCar.zul", null, args);
                System.out.println("Navigating to Edit Car form for car ID: " + carId);
            } else {
                System.err.println("Car with ID: " + carId + " not found.");
            }
        } catch (Exception e) {
            System.err.println("Error navigating to edit car form: " + e.getMessage());
            e.printStackTrace();
        }
    }
}