package com.taxi.controller;

import com.taxi.config.AppConfig;
import com.taxi.model.Manufacturer;
import com.taxi.services.ManufacturerServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/manufacturers/add")
public class AddManufacturerController extends HttpServlet {
    private final AnnotationConfigApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(AppConfig.class);
    private final ManufacturerServiceImpl manufacturerService
            = applicationContext.getBean(ManufacturerServiceImpl.class);
    private Manufacturer manufacturer
            = new Manufacturer();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/createManufacturer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        manufacturer.setName(name);
        String country = req.getParameter("country");
        manufacturer.setCountry(country);
        manufacturer = manufacturerService.create(manufacturer);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
