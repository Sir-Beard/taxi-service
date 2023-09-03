package com.taxi.controller;

import com.taxi.services.ManufacturerServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/manufacturers/delete")
public class DeleteManufacturerController extends AbstractController {
    private final ManufacturerServiceImpl manufacturerService
            = getApplicationContext().getBean(ManufacturerServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String manufacturerIdParam = req.getParameter("manufacturerId");
        Long id = Long.parseLong(manufacturerIdParam);
        manufacturerService.delete(id);
        req.getRequestDispatcher("/WEB-INF/views/displayAllManufacturers.jsp").forward(req, resp);
    }
}
