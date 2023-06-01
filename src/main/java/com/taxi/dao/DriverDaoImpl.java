package com.taxi.dao;

import com.taxi.dao.interfaces.DriverDao;
import com.taxi.exception.DataProcessingException;
import com.taxi.model.Driver;
import com.taxi.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DriverDaoImpl implements DriverDao {
    private ConnectionUtil connectionUtil;

    public DriverDaoImpl(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Driver create(Driver driver) {
        String queryCreate = "INSERT INTO drivers (`name`, `licenseNumber`) VALUES (?, ?)";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryCreate,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long insertedId = resultSet.getObject(1, Long.class);
                driver.setId(insertedId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create driver: "
                    + driver, e);
        }
        return driver;
    }

    @Override
    public Driver get(Long id) {
        String queryGet
                = "SELECT * FROM drivers WHERE id = ? AND is_deleted = FALSE";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryGet)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Driver driver = new Driver();
            if (resultSet.next()) {
                driver = getDriver(resultSet);
            }
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver, id = "
                    + id, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        String queryGetAll
                = "SELECT * FROM drivers WHERE is_deleted = FALSE";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                Statement statement
                        = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(queryGetAll);
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                Driver driver = getDriver(resultSet);
                drivers.add(driver);
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers.", e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String queryUpdate
                = "UPDATE drivers SET name = ?, licenseNumber = ? "
                + "WHERE id = ?  AND is_deleted = FALSE";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryUpdate)
        ) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.setLong(3, driver.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return driver;
            } else {
                throw new RuntimeException("Failed to update driver " + driver);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update driver: "
                    + driver, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String queryDelete
                = "UPDATE drivers SET is_deleted = TRUE WHERE id = ?";
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
            throw new DataProcessingException("Can't delete driver, id = "
                    + id, e);
        }
    }

    private Driver getDriver(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("licenseNumber");
        Boolean isDeleted = resultSet.getBoolean("is_deleted");
        return new Driver(id, name, licenseNumber, isDeleted);
    }
}
