package com.example.proyectofinalprograiimvc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String Login(){
        log.info("Ejecutando el controlador Spring MVC");
        return "Home/Index";
    }
}
