package com.taxi.controller;

import com.taxi.config.AppConfig;
import com.taxi.model.Driver;
import com.taxi.services.DriverServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/DisplayAllDriversController")
public class DisplayAllDriversController extends HttpServlet {
    private final AnnotationConfigApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(AppConfig.class);
    private final DriverServiceImpl driverService
            = applicationContext.getBean(DriverServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<Driver> allDrivers = driverService.getAll();
        allDrivers = sortById(allDrivers);
        resp.setContentType("text/html");
        req.setAttribute("drivers", allDrivers);
        req.getRequestDispatcher("/WEB-INF/views/displayAllDrivers.jsp").forward(req, resp);
    }

    private Set<Driver> sortById(Set<Driver> allDrivers) {
        List<Driver> list = new ArrayList<>(allDrivers);
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).getId() > list.get(i + 1).getId()) {
                    list.add(i, list.get(i + 1));
                    list.remove(i + 2);
                    isSorted = false;
                }
            }
        }
        allDrivers = new LinkedHashSet<>(list);
        return allDrivers;
    }
}
