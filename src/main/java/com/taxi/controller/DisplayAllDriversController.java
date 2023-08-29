package com.taxi.controller;

import com.taxi.config.AppConfig;
import com.taxi.model.Driver;
import com.taxi.services.DriverServiceImpl;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/drivers")
public class DisplayAllDriversController extends HttpServlet {
    private final AnnotationConfigApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(AppConfig.class);
    private final DriverServiceImpl driverService
            = applicationContext.getBean(DriverServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<Driver> allDrivers = driverService.getAll();
        resp.setContentType("text/html");
        req.setAttribute("drivers", allDrivers);
        req.getRequestDispatcher("/WEB-INF/views/displayAllDrivers.jsp").forward(req, resp);
    }
}
