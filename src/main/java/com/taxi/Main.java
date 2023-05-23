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
        manufacturer = manufacturerDao.create(manufacturer);
        System.out.println("created " + manufacturer);

        //UPDATE
        manufacturer.setName("Chinkara");
        manufacturer.setCountry("India");
        manufacturerDao.update(manufacturer);
        System.out.println("updated " + manufacturer);

        //GET ALL
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        for (Manufacturer m : manufacturerList) {
            System.out.println(m);
        }

        //GET
        manufacturerDao.get(4L);
        System.out.println("get method with id " + manufacturerDao.get(4L));

        //DELETE
        System.out.println("id to delete: " + manufacturer.getId());
        manufacturerDao.delete(manufacturer.getId());
    }
}
