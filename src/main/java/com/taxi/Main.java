package com.taxi;

import com.taxi.config.AppConfig;
import com.taxi.model.Driver;
import com.taxi.model.Manufacturer;
import com.taxi.services.DriverServiceImpl;
import com.taxi.services.ManufacturerServiceImpl;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(AppConfig.class);
        //applicationContext.refresh();

        // manufacturer
        ManufacturerServiceImpl manufacturerService
                = applicationContext.getBean(ManufacturerServiceImpl.class);
        Manufacturer manufacturer
                = new Manufacturer();

        //CREATE Manufacturer
        manufacturer.setName("Plasan");
        manufacturer.setCountry("Israel");
        manufacturer = manufacturerService.create(manufacturer);
        System.out.println("Manufacturer created: " + manufacturer);

        //UPDATE Manufacturer
        manufacturer.setId(15L);
        manufacturer.setName("Plasan2");
        manufacturer.setCountry("Israel");
        manufacturerService.update(manufacturer);
        System.out.println("Manufacturer updated: " + manufacturer);

        //GET ALL Manufacturer
        List<Manufacturer> manufacturerList = manufacturerService.getAll();
        for (Manufacturer m : manufacturerList) {
            System.out.println(m);
        }

        //GET Manufacturer
        manufacturerService.get(4L);
        System.out.println("Get manufacturer with id "
                + manufacturerService.get(4L).getId()
                + " - "
                + manufacturerService.get(4L));

        //DELETE Manufacturer
        System.out.println("Manufacturer with id "
                + manufacturer.getId()
                + "to delete: "
                + manufacturer.getId());
        manufacturerService.delete(manufacturer.getId());

        // driver
        DriverServiceImpl driverService
                = applicationContext.getBean(DriverServiceImpl.class);
        Driver driver
                = new Driver();

        //CREATE Driver
        driver.setName("testDriver");
        driver.setLicenseNumber("testLicense");
        driver = driverService.create(driver);
        System.out.println("Driver created: " + driver);

        //UPDATE Driver
        driver.setId(3L);
        driver.setName("testDriver2");
        driver.setLicenseNumber("testLicense2");
        driver = driverService.update(driver);
        System.out.println("Driver updated: " + driver);

        //GET ALL Driver
        List<Driver> driversList = driverService.getAll();
        for (Driver d : driversList) {
            System.out.println(d);
        }

        //GET Driver
        driverService.get(1L);
        System.out.println("Get driver with id "
                + driverService.get(1L).getId()
                + " - "
                + driverService.get(1L));

        //DELETE Driver
        System.out.println("Driver with id "
                + driver.getId()
                + " to delete: "
                + driverService.get(driver.getId()));
        driverService.delete(driver.getId());
    }
}
