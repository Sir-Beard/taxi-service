package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.interfaces.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();

        //CREATE
        manufacturer.setName("Rimac");
        manufacturer.setCountry("Croatia");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturer);

        //UPDATE
        manufacturer.setId(11L);
        manufacturer.setName("Rimac");
        manufacturer.setCountry("Croatia");
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
        manufacturerDao.delete(5L);
    }
}