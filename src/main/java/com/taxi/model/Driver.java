package com.taxi.model;

public class Driver {
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
}
