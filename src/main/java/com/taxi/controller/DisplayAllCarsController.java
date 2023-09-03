package com.taxi.controller;

import com.taxi.model.Car;
import com.taxi.services.CarServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars")
public class DisplayAllCarsController extends AbstractController {
    private final CarServiceImpl carService
            = getApplicationContext().getBean(CarServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Car> allCars = carService.getAll();
        req.setAttribute("cars", allCars);
        req.getRequestDispatcher("/WEB-INF/views/displayAllCars.jsp").forward(req, resp);
    }
}
