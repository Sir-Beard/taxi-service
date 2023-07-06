package com.taxi.services.interfaces;

import com.taxi.model.Driver;
import java.util.Set;

public interface DriverService {
    Driver create(Driver driver);

    Driver get(Long id);

    Set<Driver> getAll();

    Driver update(Driver driver);

    boolean delete(Long id);
}
