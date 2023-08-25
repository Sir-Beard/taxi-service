package com.taxi.controller;

import com.taxi.config.AppConfig;
import com.taxi.model.Car;
import com.taxi.model.Driver;
import com.taxi.services.CarServiceImpl;
import com.taxi.services.DriverServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/AddDriverToCarController")
public class AddDriverToCarController extends HttpServlet {
    private final AnnotationConfigApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(AppConfig.class);
    private Driver driver
            = new Driver();
    private Car car
            = new Car();
    private CarServiceImpl carService
            = applicationContext.getBean(CarServiceImpl.class);
    private DriverServiceImpl driverService
            = applicationContext.getBean(DriverServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/addDriverToCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String driverId = req.getParameter("driverId");
        String carId = req.getParameter("carId");
        driver = driverService.get(Long.parseLong(driverId));
        System.out.println(Long.parseLong(driverId));
        car = carService.get(Long.parseLong(carId));
        System.out.println(Long.parseLong(carId));
        System.out.println(driver);
        System.out.println(car);
        carService.addDriverToCar(driver, car);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
