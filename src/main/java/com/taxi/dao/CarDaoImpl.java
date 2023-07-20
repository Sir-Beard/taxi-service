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
        Connection connection = connectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            createCarDataInDb(car, connection);
            addCarDrivers(car, connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback is not successful", ex);
            }
            throw new DataProcessingException("Can't create car: "
                    + car, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Couldn't close the connection", e);
            }
        }
        return car;
    }

    private Car createCarDataInDb(Car car, Connection connection) throws SQLException {
        String queryCreate = "INSERT INTO cars (manufacturer_id, model) VALUES (?, ?)";
        try (
                PreparedStatement statement
                        = connection.prepareStatement(queryCreate,
                        PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, car.getManufacturer().getId());
            statement.setString(2, car.getModel());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long insertedId = resultSet.getObject(1, Long.class);
            car.setId(insertedId);
        }
        return car;
    }

    @Override
    public Car update(Car car) {
        Connection connection = connectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            updateCarDataInDb(car, connection);
            removeCarDrivers(car, connection);
            addCarDrivers(car, connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback is not successful", ex);
            }
            throw new DataProcessingException("Can't update car: "
                    + car, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Couldn't close the connection", e);
            }
        }
        return car;
    }

    private Car updateCarDataInDb(Car car, Connection connection) throws SQLException {
        String queryUpdate
                = "UPDATE cars SET manufacturer_id = ?, model = ?"
                + "WHERE id = ?  AND is_deleted = FALSE";
        try (
                PreparedStatement statement
                        = connection.prepareStatement(queryUpdate)
        ) {
            statement.setLong(1, car.getManufacturer().getId());
            statement.setString(2, car.getModel());
            statement.setLong(3, car.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated <= 0) {
                throw new RuntimeException("Failed to update car " + car);
            }
        }
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        String queryGet = "SELECT c.id AS 'C_ID', c.manufacturer_id AS 'M_ID_Link', "
                + "c.model AS 'C_model', c.is_deleted, "
                + "m.id AS 'M_ID', m.name AS 'M_name', m.country AS 'M_country', m.is_deleted, "
                + "d.id AS 'D_ID', d.name AS 'D_Name', d.licenseNumber AS 'D_LicNum', d.is_deleted "
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
            while (resultSet.next()) {
                car = getCar(resultSet);
                car.setDrivers(getAllDriversFromOneCar(connection, car));
            }
            return Optional.ofNullable(car);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car, id = "
                    + id, e);
        }
    }

    private Set<Driver> getAllDriversFromOneCar(Connection connection, Car car) throws SQLException {
        String queryGetAllDriversFromOneCar
                = "SELECT d.id AS 'D_ID', d.name AS 'D_Name', "
                + "d.licenseNumber AS 'D_LicNum', d.is_deleted "
                + "FROM drivers d "
                + "JOIN cars_drivers cd ON d.id = cd.driver_id "
                + "JOIN cars c ON c.id = cd.car_id "
                + "WHERE c.is_deleted = FALSE AND cd.car_id = ?;";
        Set<Driver> drivers = new HashSet<>();
        try (
                PreparedStatement statement
                        = connection.prepareStatement(queryGetAllDriversFromOneCar)
        ) {
            statement.setLong(1, car.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                drivers.add(getDriver(resultSet));
            }

        }
        return drivers;
    }

    @Override
    public List<Car> getAll() {
        String queryGetAll
                = "SELECT c.id AS 'C_ID', c.manufacturer_id AS 'M_ID_Link', "
                + "c.model AS 'C_model', c.is_deleted, "
                + "m.id AS 'M_ID', m.name AS 'M_name', m.country AS 'M_country', m.is_deleted "
                + "FROM cars c "
                + "JOIN manufacturers m on m.id = c.manufacturer_id "
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
                car.setDrivers(getAllDriversFromOneCar(connection, car));
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
        String queryGetAllByDriver
                = "SELECT c.id AS 'C_ID', c.manufacturer_id AS 'M_ID_Link', "
                + "c.model AS 'C_model', c.is_deleted, "
                + "d.id AS 'D_ID', d.name AS 'D_Name', d.licenseNumber AS 'D_LicNum', d.is_deleted, "
                + "m.id AS 'M_ID', m.name AS 'M_name', m.country AS 'M_country', m.is_deleted "
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
                car.setDrivers(getAllDriversFromOneCar(connection, car));
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars by driver.", e);
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        Long id = resultSet.getObject("C_ID", Long.class);
        String model = resultSet.getString("C_model");
        Boolean isDeleted = resultSet.getBoolean("is_deleted");
        car.setId(id);
        car.setModel(model);
        car.setDeleted(isDeleted);
        car.setManufacturer(getManufacturer(resultSet));
        return car;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        Long id = resultSet.getObject("M_ID", Long.class);
        String name = resultSet.getString("M_name");
        String country = resultSet.getString("M_country");
        Boolean isDeleted = resultSet.getBoolean("is_deleted");
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturer.setDeleted(isDeleted);
        return manufacturer;
    }

    private Driver getDriver(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("D_ID", Long.class);
        String name = resultSet.getString("D_Name");
        String licenseNumber = resultSet.getString("D_LicNum");
        Boolean isDeleted = resultSet.getBoolean("is_deleted");
        return new Driver(id, name, licenseNumber, isDeleted);
    }

    private void removeCarDrivers(Car car, Connection connection) throws SQLException {
        String queryRemoveCarDriver = "DELETE FROM cars_drivers WHERE car_id = ?";
        try (
                PreparedStatement statement
                        = connection.prepareStatement(queryRemoveCarDriver)
        ) {
            statement.setLong(1, car.getId());
            statement.executeUpdate();
        }
    }

    private void addCarDrivers(Car car, Connection connection) throws SQLException {
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
        }
    }
}
