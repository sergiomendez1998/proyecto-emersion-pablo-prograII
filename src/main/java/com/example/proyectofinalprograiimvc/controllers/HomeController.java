package com.example.proyectofinalprograiimvc.controllers;

import com.example.proyectofinalprograiimvc.servicios.*;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private RolServiceImpl rolService;

    @Autowired
    private EstadoServiceImpl estadoService;

    @Autowired
    private TipoExamenServiceImpl tipoExamenService;

    @Autowired
    private TipoMuestraServiceImpl tipoMuestraService;

    @Autowired
    private UnidadMedidaServiceImpl unidadMedidaService;

    @Autowired
    private TipoSoporteServiceImpl tipoSoporteService;

    @GetMapping("/")
    @RolesAllowed({"ROLE_ADMIN"})
    public String Login(){
        log.info("Ejecutando el controlador Spring MVC");
        return "Home/Index";
    }

    @ModelAttribute
    public void defaultAttribute(Model model){
        model.addAttribute("items", itemService.listarTodos());
        model.addAttribute("roles", rolService.listarTodos());
        model.addAttribute("estados", estadoService.listarTodos());
        model.addAttribute("tipoExamenes", tipoExamenService.listarTodos());
        model.addAttribute("tipoMuestras", tipoMuestraService.listarTodos());
        model.addAttribute("unidadMedidas", unidadMedidaService.listarTodos());
        model.addAttribute("tipoSoportes", tipoSoporteService.listarTodos());
    }
}
