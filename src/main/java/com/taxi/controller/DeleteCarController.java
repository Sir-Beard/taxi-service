package com.taxi.controller;

import com.taxi.services.CarServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/delete")
public class DeleteCarController extends AbstractController {
    private final CarServiceImpl carService
            = getApplicationContext().getBean(CarServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carIdParam = req.getParameter("carId");
        Long id = Long.parseLong(carIdParam);
        carService.delete(id);
        req.getRequestDispatcher("/WEB-INF/views/displayAllCars.jsp").forward(req, resp);
    }
}
