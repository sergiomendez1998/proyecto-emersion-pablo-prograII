package com.example.proyectofinalprograiimvc.controllers;

import com.example.proyectofinalprograiimvc.Utils;
import com.example.proyectofinalprograiimvc.dto.SolicitudDTO;
import com.example.proyectofinalprograiimvc.modelo.DetalleSolicitud;
import com.example.proyectofinalprograiimvc.modelo.Solicitud;
import com.example.proyectofinalprograiimvc.modelo.Usuario;
import com.example.proyectofinalprograiimvc.servicios.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SolicitudController {

   @Autowired
   private SolicitudServiceImpl solicitudService;

   @Autowired
   private TipoSoporteServiceImpl tipoSoporteService;

   @Autowired
   private ClienteServiceImpl clienteService;

   @Autowired
   private ItemServiceImpl itemService;

   @Autowired
   private UsuarioServiceImpl usuarioService;

   @Autowired
   private DetalleSolicitudServiceImpl detalleSolicitudService;

    @GetMapping("/Solicitud")
    public String SolicitudIn(SolicitudDTO solicitudDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogueado = usuarioService.buscarPorCorreo(authentication.getName());
        return usuarioLogueado.getTipoUsuario().equals("interno")? "Solicitud/Interna":"Solicitud/Externa";
    }

   @PostMapping("/guardarSolicitud")
    public String guardarSolicitud(@Valid SolicitudDTO solicitudDTO, BindingResult bindingResult, RedirectAttributes redirect){

       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       Usuario usuarioLogueado = usuarioService.buscarPorCorreo(authentication.getName());

       if (bindingResult.hasErrors()) {
           return "redirect:/";
       }

         Solicitud nuevaSolicitud = new Solicitud();

         nuevaSolicitud.setCodigoSolicitud(Utils.generarNumeroSolicitudRandom(usuarioLogueado.getTipoUsuario()));
         nuevaSolicitud.setNumeroSoporte(solicitudDTO.getNumeroSoporte());
         nuevaSolicitud.setObservacion(solicitudDTO.getObservacion());
         nuevaSolicitud.setCorreo(solicitudDTO.getCorreo());
         nuevaSolicitud.setTipoSoporte(tipoSoporteService.buscarPorId(solicitudDTO.getTipoSoporteId()));
        if (usuarioLogueado.getTipoUsuario().equalsIgnoreCase("externo")) {
            nuevaSolicitud.setCliente(clienteService.buscarPorId(usuarioLogueado.getId()));
        } else {
           nuevaSolicitud.setCliente(clienteService.buscarPorId(solicitudDTO.getClienteId()));
        }

        solicitudService.guardar(nuevaSolicitud);

        solicitudDTO.getItems().forEach(item -> {
            DetalleSolicitud nuevoDetalleSolicitud = new DetalleSolicitud();
            nuevoDetalleSolicitud.setSolicitud(nuevaSolicitud);
            nuevoDetalleSolicitud.setItem(itemService.buscarPorId(item.getId()));
            detalleSolicitudService.guardar(nuevoDetalleSolicitud);
        });

        redirect.addFlashAttribute("mensaje", "Solicitud No: "+ nuevaSolicitud.getCodigoSolicitud()+ " creada exitosamente");

        return "redirect:/";
    }

    @DeleteMapping("/eliminarSolicitud/{id}")
    public String eliminarSolicitud(@PathVariable Long id, RedirectAttributes redirect){

        Solicitud solicitudEncotrada = solicitudService.buscarPorId(id);

        solicitudEncotrada.setEliminado(true);
        solicitudEncotrada.getDetalleSolicitudList().forEach(detalleSolicitud -> {
            detalleSolicitud.setEliminado(true);
            detalleSolicitudService.actualizar(detalleSolicitud);
        });

        solicitudEncotrada.getMuestraList().forEach(muestra -> {
             muestra.getItemMuestraList().forEach(itemMuestra -> {
                 itemMuestra.setEliminado(true);
             });
         });

       solicitudEncotrada.getMuestraList().forEach(muestra -> {
          muestra.setEliminado(true);
       });

        solicitudService.actualizar(solicitudEncotrada);
        redirect.addFlashAttribute("mensaje", "Solicitud No: "+ solicitudEncotrada.getCodigoSolicitud()+ " eliminada exitosamente");
        return "redirect:/";
    }

}
