package com.taxi.controller;

import com.taxi.model.Driver;
import com.taxi.services.DriverServiceImpl;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/drivers")
public class DisplayAllDriversController extends AbstractController {
    private final DriverServiceImpl driverService
            = getApplicationContext().getBean(DriverServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<Driver> allDrivers = driverService.getAll();
        resp.setContentType("text/html");
        req.setAttribute("drivers", allDrivers);
        req.getRequestDispatcher("/WEB-INF/views/displayAllDrivers.jsp").forward(req, resp);
    }
}
