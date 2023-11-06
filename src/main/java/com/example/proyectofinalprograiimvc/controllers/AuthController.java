package com.example.proyectofinalprograiimvc.controllers;

import com.example.proyectofinalprograiimvc.modelo.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AuthController {

    @GetMapping("/login")
    public String Login(){
        log.info("Ejecutando el controlador Spring MVC");
        return "Auth/Login";
    }

    @GetMapping("/register")
    public String Register(Cliente cliente){
        log.info("Ejecutando el controlador Spring MVC");
        return "Auth/Register";
    }
}
