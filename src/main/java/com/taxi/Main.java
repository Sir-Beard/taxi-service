package com.taxi;

import com.taxi.dao.interfaces.ManufacturerDao;
import com.taxi.model.Manufacturer;
import com.taxi.util.ConnectionUtil;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(ConnectionUtil.class);
        ManufacturerDao manufacturerDao
                = applicationContext.getBean(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();

        //CREATE
        manufacturer.setName("Ajanta Group");
        manufacturer.setCountry("India");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturer);

        //UPDATE
        manufacturer.setId(12L);
        manufacturer.setName("testName");
        manufacturer.setCountry("testCountry");
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturer);

        //GET ALL
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        for (Manufacturer m : manufacturerList) {
            System.out.println(m);
        }

        //GET
        manufacturerDao.get(4L);
        System.out.println(manufacturerDao.get(4L));

        //DELETE
        manufacturerDao.delete(12L);
    }
}
