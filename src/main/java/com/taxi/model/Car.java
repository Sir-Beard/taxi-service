package com.taxi.model;

import java.util.HashSet;
import java.util.Set;

public class Car {
    private Long id;
    private String model;
    private Manufacturer manufacturer;
    private Set<Driver> drivers;
    private Boolean isDeleted;

    public Car() {
        this.drivers = new HashSet<>();
    }

    public Car(Long id, String model, Boolean isDeleted) {
        this.id = id;
        this.model = model;
        this.isDeleted = isDeleted;
    }

    public Car(Long id, Manufacturer manufacturer, String model, Boolean isDeleted) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.isDeleted = isDeleted;
    }

    public Car(Long id, String model, Manufacturer manufacturer, Set<Driver> drivers, Boolean isDeleted) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.drivers = new HashSet<>(drivers);
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + id
                + ", model='" + model + '\''
                + ", manufacturer=" + manufacturer
                + ", drivers=" + drivers
                + ", isDeleted=" + isDeleted
                + '}';
    }
}
