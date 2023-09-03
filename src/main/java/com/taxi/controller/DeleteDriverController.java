package com.taxi.controller;

import com.taxi.services.DriverServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/drivers/delete")
public class DeleteDriverController extends AbstractController {
    private final DriverServiceImpl driverService
            = getApplicationContext().getBean(DriverServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String driverIdParam = req.getParameter("driverId");
        Long id = Long.parseLong(driverIdParam);
        driverService.delete(id);
        req.getRequestDispatcher("/WEB-INF/views/displayAllDrivers.jsp").forward(req, resp);
    }
}
