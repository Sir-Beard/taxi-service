package com.taxi.controller;

import com.taxi.config.AppConfig;
import com.taxi.services.CarServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/CarDeleteController/delete")
public class CarDeleteController extends HttpServlet {

    private final AnnotationConfigApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(AppConfig.class);

    private final CarServiceImpl carService
            = applicationContext.getBean(CarServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carIdParam = req.getParameter("carId");
        Long id = Long.parseLong(carIdParam);
        carService.delete(id);
        req.getRequestDispatcher("/WEB-INF/views/displayAllCars.jsp").forward(req, resp);
    }
}
