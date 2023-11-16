package com.example.proyectofinalprograiimvc.controllers;

import com.example.proyectofinalprograiimvc.Utils;
import com.example.proyectofinalprograiimvc.dto.SolicitudDTO;
import com.example.proyectofinalprograiimvc.modelo.DetalleSolicitud;
import com.example.proyectofinalprograiimvc.modelo.Item;
import com.example.proyectofinalprograiimvc.modelo.Solicitud;
import com.example.proyectofinalprograiimvc.modelo.Usuario;
import com.example.proyectofinalprograiimvc.servicios.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private TipoExamenServiceImpl tipoExamenService;

    private Map<String, List<Item>> map = new HashMap<>();

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

    @PostMapping("/procesar")
    public String procesarFormulario(@RequestParam(name = "detalles", required = false) List<String> detalles) {
        // Realiza el procesamiento con los detalles recibidos
        if (detalles != null) {
            for (String detalle : detalles) {
                // Realiza acciones con cada detalle
                System.out.println("Detalle seleccionado: " + detalle);
            }
        }

        // Puedes redirigir a otra página o devolver una respuesta según tus necesidades
        return "redirect:/";
    }

    @ModelAttribute
    public void defaultAttribute(Model model){
        tipoExamenService.listarTodos().forEach(tipoExamen -> {
            map.put(tipoExamen.getNombre(), itemService.listarTodos().stream().filter(x -> x.getTipoExamen().getId().equals(tipoExamen.getId())).toList());
        });
        model.addAttribute("itemsAgrupadosPorTipoExamen", map);
    }

}
