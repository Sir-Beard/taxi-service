package com.taxi;

import com.taxi.config.AppConfig;
import com.taxi.model.Car;
import com.taxi.model.Driver;
import com.taxi.model.Manufacturer;
import com.taxi.services.CarServiceImpl;
import com.taxi.services.DriverServiceImpl;
import com.taxi.services.ManufacturerServiceImpl;
import java.util.List;
import java.util.Set;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(AppConfig.class);

        final ManufacturerServiceImpl manufacturerService
                = applicationContext.getBean(ManufacturerServiceImpl.class);
        Manufacturer manufacturer
                = new Manufacturer();

        System.out.println("                    MANUFACTURER CREATED");
        manufacturer.setName("testManufName19");
        manufacturer.setCountry("testManufCountry19");
        manufacturer = manufacturerService.create(manufacturer);
        System.out.println("Manufacturer created: " + manufacturer);

        System.out.println("                    MANUFACTURER UPDATED");
        manufacturer.setName("testManufName19Updated");
        manufacturer.setCountry("testManufCountry19Updated");
        manufacturerService.update(manufacturer);
        System.out.println("Manufacturer updated: " + manufacturer);

        System.out.println("                    MANUFACTURER GETALL");
        List<Manufacturer> manufacturerList = manufacturerService.getAll();
        manufacturerList.stream().forEach(s -> System.out.println(s));

        System.out.println("                    MANUFACTURER GET ONE");
        manufacturer = manufacturerService.get(20L);
        System.out.println("Get manufacturer with id "
                + manufacturerService.get(20L).getId()
                + " - "
                + manufacturerService.get(20L));

        System.out.println("                    MANUFACTURER DELETED");
        System.out.println("Manufacturer with id "
                + manufacturer.getId()
                + " to delete: "
                + manufacturer.getId());
        manufacturerService.delete(manufacturer.getId());

        System.out.println();

        final DriverServiceImpl driverService
                = applicationContext.getBean(DriverServiceImpl.class);
        Driver driver
                = new Driver();

        System.out.println("                    DRIVER CREATED");
        driver.setName("testDriverName17");
        driver.setLicenseNumber("testLicenseNumber17");
        driver = driverService.create(driver);
        System.out.println("Driver created: " + driver);

        System.out.println("                    DRIVER UPDATED");
        driver.setName("testDriverName17Updated");
        driver.setLicenseNumber("testLicenseNumber17Updated");
        driver = driverService.update(driver);
        System.out.println("Driver updated: " + driver);

        System.out.println("                    DRIVER GET ALL");
        Set<Driver> driversList = driverService.getAll();
        driversList.stream().forEach(System.out::println);

        System.out.println("                    DRIVER GET ONE");
        driver = driverService.get(5L);
        System.out.println("Get driver with id "
                + driverService.get(5L).getId()
                + " - "
                + driverService.get(5L));

        System.out.println("                    DRIVER DELETED");
        System.out.println("Driver with id "
                + driver.getId()
                + " to delete: "
                + driverService.get(driver.getId()));
        driverService.delete(driver.getId());

        System.out.println();

        final CarServiceImpl carService
                = applicationContext.getBean(CarServiceImpl.class);
        Car car
                = new Car();

        System.out.println("                    CAR CREATED (plus check for manufacturer, plus check for driver con)");
        //car.getDrivers().add(driver);
        car.setManufacturer(manufacturer);
        car.setModel("testCarModel21");
        car = carService.create(car);
        System.out.println("Car model created: " + car);

        System.out.println("                    CAR UPDATED");
        car.setModel("testCarModel21Updated");
        car = carService.update(car);
        System.out.println("Car model updated: " + car);

        System.out.println("                    CAR GET ALL");
        List<Car> carList = carService.getAll();
        carList.stream().forEach(System.out::println);

        System.out.println("                    CAR GET ONE");
        car = carService.get(28L);
        System.out.println("Get car model with id "
                + carService.get(28L).getId()
                + " - "
                + carService.get(28L));

        System.out.println("                    CAR DELETED");
        System.out.println("Car model with id "
                + car.getId()
                + " to delete: "
                + carService.get(car.getId()));
        carService.delete(car.getId());

        System.out.println("                    CAR ADD DRIVER (check cars_drivers for connection)");
        System.out.println("Adding driver to car...");
        carService.addDriverToCar(driver, car);
        System.out.println("... done.");

        System.out.println("                    CAR DELETE DRIVER");
        System.out.println("Removing driver from car...");
        carService.removeDriverFromCar(driver, car);
        System.out.println("... done.");

        System.out.println("                    GET ALL CARs BY DRIVER");
        List<Car> carListByDriver = carService.getAllByDriver(2L);
        carListByDriver.stream().forEach(x -> System.out.println(x));
    }
}
