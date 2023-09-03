package com.taxi.controller;

import com.taxi.model.Driver;
import com.taxi.services.DriverServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/drivers/add")
public class AddDriverController extends AbstractController {
    private final DriverServiceImpl driverService
            = getApplicationContext().getBean(DriverServiceImpl.class);
    private final Driver driver
            = new Driver();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/createDriver.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        driver.setName(name);
        String country = req.getParameter("licenceNumber");
        driver.setLicenseNumber(country);
        driverService.create(driver);
        resp.sendRedirect(req.getContextPath() + "/drivers");
    }
}
