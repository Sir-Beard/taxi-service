package com.taxi.controller;

import com.taxi.config.AppConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet(description = "AnnotationConfigApplicationContext handler for all Controllers")
public class AbstractController extends HttpServlet {
    private final AnnotationConfigApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(AppConfig.class);

    public AbstractController() {
    }

    public AnnotationConfigApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
