package com.taxi.controller;

import com.taxi.model.Manufacturer;
import com.taxi.services.ManufacturerServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/manufacturers")
public class DisplayAllManufacturersController extends AbstractController {
    private final ManufacturerServiceImpl manufacturerService
            = getApplicationContext().getBean(ManufacturerServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Manufacturer> allManufacturers = manufacturerService.getAll();
        req.setAttribute("manufacturers", allManufacturers);
        req.getRequestDispatcher("/WEB-INF/views/displayAllManufacturers.jsp").forward(req, resp);
    }
}
