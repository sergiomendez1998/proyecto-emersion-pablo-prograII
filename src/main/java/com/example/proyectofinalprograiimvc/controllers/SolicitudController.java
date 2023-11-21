package com.example.proyectofinalprograiimvc.controllers;

import com.example.proyectofinalprograiimvc.Utils;
import com.example.proyectofinalprograiimvc.dto.SolicitudDTO;
import com.example.proyectofinalprograiimvc.modelo.*;
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

    @GetMapping("/Solicitud/Create")
    public String SolicitudIn(SolicitudDTO solicitudDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogueado = usuarioService.buscarPorCorreo(authentication.getName());
        return usuarioLogueado.getTipoUsuario().equals("interno")? "Solicitud/Interna":"Solicitud/Externa";
    }

    @GetMapping("/Solicitud")
    public String listarSolicitud(){
        return "Solicitud/listarSolicitud";
    }

    @PostMapping("/Solicitud/Create")
    public String guardarSolicitud(@Valid SolicitudDTO solicitudDTO, BindingResult bindingResult, RedirectAttributes redirect, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogueado = usuarioService.buscarPorCorreo(authentication.getName());
       try {

           if (bindingResult.hasErrors()) {
               return usuarioLogueado.getTipoUsuario().equals("interno")? "Solicitud/Interna":"Solicitud/Externa";
           }

           Solicitud nuevaSolicitud = new Solicitud();

           nuevaSolicitud.setCodigoSolicitud(Utils.generarNumeroSolicitudRandom(usuarioLogueado.getTipoUsuario()));
           nuevaSolicitud.setNumeroSoporte(solicitudDTO.getNumeroSoporte());
           nuevaSolicitud.setObservacion(solicitudDTO.getObservacion());
           nuevaSolicitud.setCorreo(solicitudDTO.getCorreo());
           nuevaSolicitud.setTipoSoporte(tipoSoporteService.buscarPorId(solicitudDTO.getTipoSoporteId()));

           if (usuarioLogueado.getTipoUsuario().equalsIgnoreCase("externo")) {
               nuevaSolicitud.setCliente(clienteService.buscarPorUsuarioId(usuarioLogueado.getId()));
           } else {

               if (solicitudDTO.getClienteCui().isEmpty() && solicitudDTO.getClienteCui().isBlank()) {
                   bindingResult.rejectValue("clienteCui", "error.clienteCui", "El CUI del cliente es requerido");
                   return usuarioLogueado.getTipoUsuario().equals("interno")? "Solicitud/Interna":"Solicitud/Externa";
               }

               Cliente cliente = clienteService.buscarPorCui(solicitudDTO.getClienteCui());

               if (cliente == null){
                   bindingResult.rejectValue("clienteCui", "error.clienteCui", "El CUI del cliente no existe");
                   model.addAttribute("error", "El Cliente aun no tiene cuenta, por favor registrelo antes de crear la solicitud");
                   return usuarioLogueado.getTipoUsuario().equals("interno") ? "Solicitud/Interna" : "Solicitud/Externa";
               }

               nuevaSolicitud.setCliente(cliente);
           }

           solicitudService.guardar(nuevaSolicitud);

           solicitudDTO.getItems().stream().filter(itemDTO -> itemDTO.getId() != null).forEach(item -> {
               DetalleSolicitud nuevoDetalleSolicitud = new DetalleSolicitud();
               nuevoDetalleSolicitud.setSolicitud(nuevaSolicitud);
               nuevoDetalleSolicitud.setItem(itemService.buscarPorId(item.getId()));
               detalleSolicitudService.guardar(nuevoDetalleSolicitud);
           });

           redirect.addFlashAttribute("mensaje", "Solicitud No: "+ nuevaSolicitud.getCodigoSolicitud()+ " creada exitosamente");

           return "redirect:/Solicitud/Create";
       }catch (Exception exception){
           model.addAttribute("error", exception.getMessage());
           return usuarioLogueado.getTipoUsuario().equals("interno") ? "Solicitud/Interna" : "Solicitud/Externa";
       }
    }

    @PostMapping("/Solicitud/eliminar/{id}")
    public String eliminarSolicitud(@PathVariable Long id, RedirectAttributes redirect){

        Solicitud solicitudEncotrada = solicitudService.buscarPorId(id);

        solicitudEncotrada.setEliminado(true);
        solicitudEncotrada.getDetalleSolicitudList().forEach(detalleSolicitud -> {
            detalleSolicitud.setEliminado(true);
            detalleSolicitudService.actualizar(detalleSolicitud);
        });

        if(!solicitudEncotrada.getMuestraList().isEmpty()) {
            solicitudEncotrada.getMuestraList().forEach(muestra -> {
                muestra.setEliminado(true);
                muestra.getItemMuestraList().forEach(itemMuestra -> {
                    itemMuestra.setEliminado(true);
                });
            });
        }

        solicitudService.actualizar(solicitudEncotrada);
        redirect.addFlashAttribute("mensaje", "Solicitud No: "+ solicitudEncotrada.getCodigoSolicitud()+ " eliminada exitosamente");
        return "redirect:/Solicitud";
    }

    @ModelAttribute
    public void defaultAttributeRequest(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogueado = usuarioService.buscarPorCorreo(authentication.getName());

        tipoExamenService.listarTodos().forEach(tipoExamen -> {
            map.put(tipoExamen.getNombre(), itemService.listarTodos().stream().filter(x -> x.getTipoExamen().getId().equals(tipoExamen.getId())).toList());
        });
        model.addAttribute("itemsAgrupadosPorTipoExamen", map);

        List<TipoSoporte> tipoSoportes = tipoSoporteService.listarTodos()
                .stream()
                .filter(tipoSoporte -> tipoSoporte.getTipo().equalsIgnoreCase(usuarioLogueado.getTipoUsuario()))
                .toList();

        model.addAttribute("tipoSoportes", tipoSoportes);
        model.addAttribute("solicitudes", solicitudService.listarTodos());
    }

    @GetMapping("Solicitud/Informacion/{solicitudId}")
    public String informacionGeneral(@PathVariable Long solicitudId, Model model){
        Solicitud solicitud = solicitudService.buscarPorId(solicitudId);

        Map<String,String> informacionGeneral = new HashMap<>();

        informacionGeneral.put("codigoSolicitud", solicitud.getCodigoSolicitud());
        informacionGeneral.put("numeroSoporte", solicitud.getNumeroSoporte());
        informacionGeneral.put("tipoSoporte", solicitud.getTipoSoporte().getNombre());
        informacionGeneral.put("Solicitante", solicitud.getCliente().getNombre());
        informacionGeneral.put("correo", solicitud.getCorreo());
        informacionGeneral.put("Nit", solicitud.getCliente().getNit());
        informacionGeneral.put("estado", "Creado");
        informacionGeneral.put("observacion", solicitud.getObservacion());
        informacionGeneral.put("fechaCreacion", solicitud.getFechaRecepcion().toString());
        informacionGeneral.put("cantidadItems", String.valueOf(solicitud.getDetalleSolicitudList().size()));
        informacionGeneral.put("cantidadMuestras", String.valueOf(solicitud.getMuestraList().size()));
        informacionGeneral.put("numeroExpediente", solicitud.getCliente().getNumeroExpediente());


        model.addAttribute("informacionGeneral", solicitud);
        return "redirect:/";
    }

}
