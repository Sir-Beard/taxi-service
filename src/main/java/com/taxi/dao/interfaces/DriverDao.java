package com.taxi.dao.interfaces;

import com.taxi.model.Driver;
import java.util.Optional;
import java.util.Set;

public interface DriverDao {
    Driver create(Driver driver);

    Optional<Driver> get(Long id);

    Set<Driver> getAll();

    Driver update(Driver driver);

    boolean delete(Long id);
}
