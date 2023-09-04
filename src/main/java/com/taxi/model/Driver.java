package com.taxi.model;

import java.util.Objects;

public class Driver implements Comparable<Driver> {
    private Long id;
    private String name;
    private String licenseNumber;
    private Boolean isDeleted;

    public Driver() {
    }

    public Driver(Long id, String name, String licenseNumber, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.isDeleted = isDeleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return "Driver{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", licenseNumber='" + licenseNumber + '\''
                + ", isDeleted=" + isDeleted
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        return id.equals(driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Driver o) {
        return Long.compare(this.id, o.getId());
    }
}
