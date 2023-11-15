package com.example.proyectofinalprograiimvc.controllers;

import com.example.proyectofinalprograiimvc.Utils;
import com.example.proyectofinalprograiimvc.dto.SolicitudDTO;
import com.example.proyectofinalprograiimvc.modelo.DetalleSolicitud;
import com.example.proyectofinalprograiimvc.modelo.Solicitud;
import com.example.proyectofinalprograiimvc.modelo.Usuario;
import com.example.proyectofinalprograiimvc.servicios.*;
import jakarta.jws.soap.SOAPBinding;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class SolicitudController {

    @Autowired
    private TipoExamenServiceImpl tipoExamenService;

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
    public String SolicitudIn(Principal principal,SolicitudDTO solicitudDTO){
        Usuario usuarioLogueado = usuarioService.buscarPorCorreo(principal.getName());
        return usuarioLogueado.getTipoUsuario().equals("interno")? "Solicitud/Interna":"Solicitud/Externa";
    }

    @ModelAttribute
    public void defaultAttribute(Model model){
        model.addAttribute("items", itemService.listarTodos());
        model.addAttribute("tipoExamenes", tipoExamenService.listarTodos());
        model.addAttribute("tipoSoportes", tipoSoporteService.listarTodos());
    }
   @PostMapping("/guardarSolicitud")
    public String guardarSolicitud(@Valid SolicitudDTO solicitudDTO, BindingResult bindingResult, @AuthenticationPrincipal User user, RedirectAttributes redirect){

         Usuario usuarioLogueado = usuarioService.buscarPorCorreo(user.getUsername());

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

       if (bindingResult.hasErrors()) {
           return "redirect:/";
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
