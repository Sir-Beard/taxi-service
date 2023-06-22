package com.taxi.dao;

import com.taxi.dao.interfaces.CarDao;
import com.taxi.exception.DataProcessingException;
import com.taxi.model.Car;
import com.taxi.model.Driver;
import com.taxi.model.Manufacturer;
import com.taxi.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CarDaoImpl implements CarDao {
    private final ConnectionUtil connectionUtil;

    @Autowired
    public CarDaoImpl(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Car create(Car car) {
        String queryCreate = "INSERT INTO cars (manufacturer_id, model) VALUES (?, ?)";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryCreate,
                        PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, car.getManufacturer().getId());
            statement.setString(2, car.getModel());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long insertedId = resultSet.getObject(1, Long.class);
                car.setId(insertedId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create car: "
                    + car, e);
        }
        return car;
    }

    @Override
    public Car update(Car car) {
        String queryUpdate
                = "UPDATE cars SET manufacturer_id = ?, model = ?"
                + "WHERE id = ?  AND is_deleted = FALSE";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryUpdate)
        ) {
            statement.setLong(1, car.getManufacturer().getId());
            statement.setString(2, car.getModel());
            statement.setLong(3, car.getId());
            int rowsUpdated = statement.executeUpdate();
            removeCarDriver(car, connection);
            addCarDriver(car, connection);
            if (rowsUpdated > 0) {
                return car;
            } else {
                throw new RuntimeException("Failed to update car " + car);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car: "
                    + car, e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String queryGet = "SELECT c.id AS 'Car ID', c.manufacturer_id AS 'Manufacturer ID Linked', c.model AS 'Car model', c.is_deleted, "
                + "m.id AS 'Manufacturer ID', m.name AS 'Manufacturer name', m.country AS 'Manufacturer country', m.is_deleted, "
                + "d.id AS 'Driver ID', d.name AS 'Driver Name', d.licenseNumber AS 'License Number', d.is_deleted, "
                + "cd.driver_id AS 'CD_Driver ID', cd.car_id AS 'CD_Car ID' "
                + "FROM cars c "
                + "JOIN manufacturers m on m.id = c.manufacturer_id "
                + "LEFT JOIN cars_drivers cd ON cd.car_id = c.id "
                + "LEFT JOIN drivers d ON d.id = cd.driver_id "
                + "WHERE c.id = ? AND c.is_deleted = FALSE;";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryGet)
        ) {
            Car car = null;
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
            }
            return Optional.ofNullable(car);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car, id = "
                    + id, e);
        }
    }

    @Override
    public List<Car> getAll() {
        String queryGetAll = "SELECT c.id AS 'Car ID', c.manufacturer_id AS 'Manufacturer ID Linked', c.model AS 'Car model', c.is_deleted, "
                + "m.id AS 'Manufacturer ID', m.name AS 'Manufacturer name', m.country AS 'Manufacturer country', m.is_deleted, "
                + "d.id AS 'Driver ID', d.name AS 'Driver Name', d.licenseNumber AS 'License Number', d.is_deleted, "
                + "cd.driver_id AS 'CD_Driver ID', cd.car_id AS 'CD_Car ID' "
                + "FROM cars c "
                + "JOIN manufacturers m on m.id = c.manufacturer_id "
                + "LEFT JOIN cars_drivers cd ON cd.car_id = c.id "
                + "LEFT JOIN drivers d ON d.id = cd.driver_id "
                + "WHERE c.is_deleted = FALSE;";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryGetAll)
        ) {
            ResultSet resultSet = statement.executeQuery(queryGetAll);
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                Car car = getCar(resultSet);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars.", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String queryDelete = "UPDATE cars SET is_deleted = TRUE WHERE id = ?";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryDelete)
        ) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car, id = "
                    + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String queryGetAllByDriver = "SELECT c.id AS 'Car ID', c.manufacturer_id AS 'Manufacturer ID Linked', c.model AS 'Car model', c.is_deleted, "
                + "d.id AS 'Driver ID', d.name AS 'Driver Name', d.licenseNumber AS 'License Number', d.is_deleted, "
                + "m.id AS 'Manufacturer ID', m.name AS 'Manufacturer name', m.country AS 'Manufacturer country', m.is_deleted "
                + "FROM cars c "
                + "JOIN cars_drivers cd ON c.id = cd.car_id "
                + "JOIN drivers d ON cd.driver_id = d.id "
                + "JOIN manufacturers m on m.id = c.manufacturer_id "
                + "WHERE c.is_deleted = FALSE AND cd.driver_id = ?";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryGetAllByDriver)
        ) {
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                Car car = getCar(resultSet);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars by driver.", e);
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        Long id = resultSet.getObject("Car ID", Long.class);
        String model = resultSet.getString("Car model");
        Boolean isDeleted = resultSet.getBoolean("is_deleted");
        car.setId(id);
        car.setModel(model);
        car.setDeleted(isDeleted);
        car.setManufacturer(getManuf(resultSet));
        resultSet.getObject("Driver ID", Long.class);
        if (!(resultSet.wasNull())) {
            car.setDrivers(getDrivers(resultSet));
        }
        return car;
    }

    private Manufacturer getManuf(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        Long id = resultSet.getObject("Manufacturer ID", Long.class);
        String name = resultSet.getString("Manufacturer name");
        String country = resultSet.getString("Manufacturer country");
        Boolean isDeleted = resultSet.getBoolean("is_deleted");
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturer.setDeleted(isDeleted);
        return manufacturer;
    }

    private Set<Driver> getDrivers(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver();
        final Set<Driver> drivers = new HashSet<>();
        Long id = resultSet.getObject("Driver ID", Long.class);
        String name = resultSet.getString("Driver Name");
        String licenseNumber = resultSet.getString("License Number");
        Boolean isDeleted = resultSet.getBoolean("is_deleted");
        driver.setId(id);
        driver.setName(name);
        driver.setLicenseNumber(licenseNumber);
        driver.setDeleted(isDeleted);
        drivers.add(driver);
        return drivers;
    }

    private void removeCarDriver(Car car, Connection connection) {
        String queryRemoveCarDriver = "DELETE FROM cars_drivers WHERE car_id = ?";
        try (
                PreparedStatement statement
                        = connection.prepareStatement(queryRemoveCarDriver)
        ) {
            statement.setLong(1, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't remove driver with car: " + car, e);
        }
    }

    private void addCarDriver(Car car, Connection connection) {
        String queryAddCarDriver = "INSERT INTO cars_drivers (driver_id, car_id) VALUES (?, ?)";
        try (
                PreparedStatement statement
                        = connection.prepareStatement(queryAddCarDriver)
        ) {
            Set<Driver> setDrivers = car.getDrivers();
            for (Driver driver : setDrivers) {
                statement.setLong(1, driver.getId());
                statement.setLong(2, car.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add driver with car: " + car, e);
        }
    }
}
