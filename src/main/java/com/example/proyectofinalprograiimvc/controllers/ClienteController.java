package com.example.proyectofinalprograiimvc.controllers;

import com.example.proyectofinalprograiimvc.Utils;
import com.example.proyectofinalprograiimvc.modelo.Cliente;
import com.example.proyectofinalprograiimvc.modelo.Rol;
import com.example.proyectofinalprograiimvc.servicios.ClienteServiceImpl;
import com.example.proyectofinalprograiimvc.servicios.RolServiceImpl;
import com.example.proyectofinalprograiimvc.servicios.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {
    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private RolServiceImpl rolService;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping("/guardarCliente")
    public String guardarCliente(@Valid Cliente cliente, BindingResult bindingResult){

        Rol rol = rolService.buscarPorId(2L);
        cliente.getUsuario().setRol(rol);
        cliente.getUsuario().setContrasenia(Utils.encriptarContrasenia(cliente.getUsuario().getContrasenia()));
        cliente.setNumeroExpediente(Utils.generarNumeroDeExpediente());
        cliente.getUsuario().setTipoUsuario("externo");
        cliente.getUsuario().setNombreUsuario(cliente.getNombre() + " " + cliente.getApellido());

        if(bindingResult.hasErrors()){
            return "Auth/Register";
        }

        usuarioService.guardar(cliente.getUsuario());
        clienteService.guardar(cliente);
        return "redirect:/login";
    }
}
