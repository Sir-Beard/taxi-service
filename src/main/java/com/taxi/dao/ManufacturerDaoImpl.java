package com.taxi.dao;

import com.taxi.dao.interfaces.ManufacturerDao;
import com.taxi.exception.DataProcessingException;
import com.taxi.model.Manufacturer;
import com.taxi.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final Logger logger = LogManager.getLogger(ManufacturerDaoImpl.class);
    private final ConnectionUtil connectionUtil;

    @Autowired
    public ManufacturerDaoImpl(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Connection connection = connectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            createManufacturerDataInDb(manufacturer, connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback is not successful", ex);
            }
            logger.error("An error occurred during manufacturer creation method. "
                    + "Params: manufacturer={}", manufacturer);
            throw new DataProcessingException("Can't create manufacturer: "
                    + manufacturer, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Couldn't close the connection", e);
            }
        }
        return manufacturer;
    }

    private Manufacturer createManufacturerDataInDb(Manufacturer manufacturer, Connection connection)
            throws SQLException {
        String queryCreate = "INSERT INTO manufacturers (`name`, `country`) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(queryCreate, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, manufacturer.getName());
        statement.setString(2, manufacturer.getCountry());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        Long insertedId = resultSet.getObject(1, Long.class);
        manufacturer.setId(insertedId);
        return manufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Connection connection = connectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            updateDriverDataInDb(manufacturer, connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback is not successful", ex);
            }
            logger.error("An error occurred during manufacturer update method. Params: manufacturer={}", manufacturer);
            throw new DataProcessingException("Can't update manufacturer: "
                    + manufacturer, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Couldn't close the connection", e);
            }
        }
        return manufacturer;
    }

    private Manufacturer updateDriverDataInDb(Manufacturer manufacturer, Connection connection) throws SQLException {
        String queryUpdate = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?  AND is_deleted = FALSE";
        PreparedStatement statement = connection.prepareStatement(queryUpdate);
        statement.setString(1, manufacturer.getName());
        statement.setString(2, manufacturer.getCountry());
        statement.setLong(3, manufacturer.getId());
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated <= 0) {
            throw new RuntimeException("Failed to update manufacturer " + manufacturer);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String queryGet
                = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryGet)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Manufacturer manufacturer = new Manufacturer();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            logger.error("An error occurred during manufacturer get method. Params: id={}", id);
            throw new DataProcessingException("Can't get manufacturer, id = "
                    + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String queryGetAll
                = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (
                Connection connection
                        = connectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(queryGetAll)
        ) {
            ResultSet resultSet = statement.executeQuery(queryGetAll);
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                manufacturers.add(manufacturer);
            }
            return manufacturers;
        } catch (SQLException e) {
            logger.error("An error occurred during manufacturer getAll method", e);
            throw new DataProcessingException("Can't get all manufacturers.", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String queryDelete
                = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
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
            logger.error("An error occurred during manufacturer delete method. Params: id={}", id);
            throw new DataProcessingException("Can't delete manufacturer, id = "
                    + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Boolean isDeleted = resultSet.getBoolean("is_deleted");
        return new Manufacturer(id, name, country, isDeleted);
    }
}
