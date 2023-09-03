package com.taxi.controller;

import com.taxi.model.Car;
import com.taxi.model.Driver;
import com.taxi.services.CarServiceImpl;
import com.taxi.services.DriverServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/drivers/add")
public class AddDriverToCarController extends AbstractController {
    private final CarServiceImpl carService
            = getApplicationContext().getBean(CarServiceImpl.class);
    private final DriverServiceImpl driverService
            = getApplicationContext().getBean(DriverServiceImpl.class);
    private Driver driver
            = new Driver();
    private Car car
            = new Car();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/addDriverToCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String driverId = req.getParameter("driverId");
        String carId = req.getParameter("carId");
        driver = driverService.get(Long.parseLong(driverId));
        car = carService.get(Long.parseLong(carId));
        carService.addDriverToCar(driver, car);
        resp.sendRedirect(req.getContextPath() + "/cars");
    }
}
