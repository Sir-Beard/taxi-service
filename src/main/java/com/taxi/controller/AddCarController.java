package com.taxi.controller;

import com.taxi.model.Car;
import com.taxi.model.Manufacturer;
import com.taxi.services.CarServiceImpl;
import com.taxi.services.ManufacturerServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/add")
public class AddCarController extends AbstractController {
    private final CarServiceImpl carService
            = getApplicationContext().getBean(CarServiceImpl.class);
    private final ManufacturerServiceImpl manufacturerService
            = getApplicationContext().getBean(ManufacturerServiceImpl.class);
    private final Car car
            = new Car();
    private Manufacturer manufacturer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/createCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String model = req.getParameter("model");
        String manufacturerId = req.getParameter("manufacturerId");
        manufacturer = manufacturerService.get(Long.parseLong(manufacturerId));
        car.setManufacturer(manufacturer);
        car.setModel(model);
        carService.create(car);
        resp.sendRedirect(req.getContextPath() + "/cars");
    }
}
