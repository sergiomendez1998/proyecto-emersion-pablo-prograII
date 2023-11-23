package com.example.proyectofinalprograiimvc.controllers;


import com.example.proyectofinalprograiimvc.Utils;
import com.example.proyectofinalprograiimvc.dto.MuestraDTO;
import com.example.proyectofinalprograiimvc.modelo.*;
import com.example.proyectofinalprograiimvc.repositorio.ItemMuestraRepositorio;
import com.example.proyectofinalprograiimvc.servicios.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class MuestraController {

    @Autowired
    private MuestraServiceImpl muestraService;

    @Autowired
    private UnidadMedidaServiceImpl unidadMedidaService;

    @Autowired
    private SolicitudServiceImpl solicitudService;

    @Autowired
    private TipoMuestraServiceImpl tipoMuestraService;

    @Autowired
    private DetalleSolicitudServiceImpl detalleSolicitudService;

    @Autowired
    private ItemMuestraRepositorio itemMuestraRepositorio;

    @GetMapping("/Muestra/Crear/{solicitudId}")
    public String MuestraIn(@PathVariable Long solicitudId, MuestraDTO muestraDTO, Model model){


        muestraDTO.setIdSolicitud(solicitudId);
        model.addAttribute("solicitudId", solicitudId);
        return "Muestra/Crear";
    }

    @GetMapping("/Muestra")
    public String mostrarMuestras(Model model){
        model.addAttribute("muestras", muestraService.listarTodos());
        return "Muestra/MostrarMuestras";
    }

    @PostMapping("/Muestra/Crear")
    public String guardarMuestra(@Valid MuestraDTO muestraDTO, BindingResult bindingResult, RedirectAttributes redirect, Model model){

       try {
           Date fechaIngresada = Utils.convertirFecha(muestraDTO.getFechaVencimiento());

           if (bindingResult.hasErrors()) {
               model.addAttribute("solicitudId", muestraDTO.getIdSolicitud());
               model.addAttribute("error", "Error al crear la muestra");
               return "Muestra/Crear";
           }

           if (fechaIngresada == null) {
               model.addAttribute("solicitudId", muestraDTO.getIdSolicitud());
               model.addAttribute("error", "La fecha de vencimiento no puede ser menor a la fecha actual");
               bindingResult.rejectValue("fechaVencimiento", "error.fechaVencimiento", "La fecha de vencimiento no puede ser menor a la fecha actual");
               return "Muestra/Crear";
           }

           Muestra nuevaMuestra = new Muestra();

           nuevaMuestra.setCodigoMuestra(Utils.generarNumeroMuestraRandom());
           nuevaMuestra.setPresentacion(muestraDTO.getPresentacion());
           nuevaMuestra.setCantidad(muestraDTO.getCantidad());
           TipoMuestra tipoMuestra = tipoMuestraService.buscarPorId(muestraDTO.getIdTipoMuestra());
           nuevaMuestra.setTipoMuestra(tipoMuestra);
           UnidadMedida unidadMedida = unidadMedidaService.buscarPorId(muestraDTO.getIdUnidadMedida());
           nuevaMuestra.setUnidadMedida(unidadMedida);
           nuevaMuestra.setFechaVencimiento(fechaIngresada);
           nuevaMuestra.setSolicitud(solicitudService.buscarPorId(muestraDTO.getIdSolicitud()));

           muestraService.guardar(nuevaMuestra);
           redirect.addFlashAttribute("mensaje", "Muestra creada exitosamente!");
           return "redirect:/Muestra/Crear/" + muestraDTO.getIdSolicitud();

       }catch (Exception e){
           model.addAttribute("solicitudId", muestraDTO.getIdSolicitud());
           model.addAttribute("error", e.getMessage());
           return "Muestra/Crear";
       }
    }

    @PostMapping("/eliminarMuestra/{id}")
    public String eliminarMuestra(@PathVariable Long id, RedirectAttributes redirect){
           Muestra muestra = muestraService.buscarPorId(id);
           muestra.setEliminado(true);

           muestra.getItemMuestraList().forEach(itemMuestra -> {
               itemMuestra.setEliminado(true);
               itemMuestra.getDetalleSolicitud().setAsociado(false);
           });

           muestraService.guardar(muestra);
           redirect.addFlashAttribute("mensaje", "Muestra eliminada correctamente");
          return "redirect:/Muestra";
    }

    @PostMapping("/asociarItemMuestra/{muestraId}")
    public String asociarItemMuestra(@PathVariable Long muestraId, Long solicitudDetalleId, RedirectAttributes redirect){

        Muestra muestra = muestraService.buscarPorId(muestraId);
        DetalleSolicitud detalleSolicitud = detalleSolicitudService.buscarPorId(solicitudDetalleId);

        ItemMuestra itemMuestra = new ItemMuestra();
        itemMuestra.setMuestra(muestra);
        itemMuestra.setDetalleSolicitud(detalleSolicitud);

         itemMuestraRepositorio.save(itemMuestra);

         detalleSolicitud.setAsociado(true);

         detalleSolicitudService.guardar(detalleSolicitud);

        redirect.addFlashAttribute("mensaje", "Item asociado correctamente");

        return "redirect:/Muestra/Asociar/"+muestraId;
    }

    @PostMapping("/desasociarItemMuestra/{muestraId}")
    public String desasociarItemMuestra(@PathVariable Long muestraId, Long itemId, RedirectAttributes redirect){

        Muestra muestra = muestraService.buscarPorId(muestraId);
        muestra.getItemMuestraList().forEach(itemMuestra -> {
            if (itemMuestra.getId().equals(itemId)) {
                itemMuestra.setEliminado(true);
                itemMuestra.getDetalleSolicitud().setAsociado(false);
            }
        });

        muestraService.guardar(muestra);
        redirect.addFlashAttribute("mensaje", "Item desasociado correctamente");

        return "redirect:/Muestra/Asociar/"+muestraId;
    }

    @RequestMapping (value = "/informacionGeneralMuestra/{muestraId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> informacionGeneralMuestra(@PathVariable Long muestraId, Model model){

        Muestra muestra = muestraService.buscarPorId(muestraId);

        Map<String, String> informacionGeneralMuestra = new HashMap<>();
        informacionGeneralMuestra.put("id", String.valueOf(muestra.getId()));
        informacionGeneralMuestra.put("codigoDeMuestra", muestra.getCodigoMuestra());
        informacionGeneralMuestra.put("presentacion", muestra.getPresentacion());
        informacionGeneralMuestra.put("cantidad", String.valueOf(muestra.getCantidad()));
        informacionGeneralMuestra.put("tipoDeMuestra", muestra.getTipoMuestra().getNombre());
        informacionGeneralMuestra.put("unidadDeMedida", muestra.getUnidadMedida().getNombre());
        informacionGeneralMuestra.put("fechaDeVencimiento", muestra.getFechaVencimiento().toString());
        informacionGeneralMuestra.put("solicitud", muestra.getSolicitud().getCodigoSolicitud());
        informacionGeneralMuestra.put("expediente", String.valueOf(muestra.getSolicitud().getCliente().getNumeroExpediente()));
        informacionGeneralMuestra.put("nit", muestra.getSolicitud().getCliente().getNit());
        informacionGeneralMuestra.put("fechaCreacion", muestra.getFechaCreacion().toString());
        informacionGeneralMuestra.put("estado", "Creado");
        informacionGeneralMuestra.put("cantidadItems", String.valueOf(muestra.getItemMuestraList().size()));


        return ResponseEntity.ok(informacionGeneralMuestra);
    }

    @ModelAttribute
    public void defaultAttributeRequest(Model model){
        List<UnidadMedida> unidadMedidas = unidadMedidaService.listarTodos();
        model.addAttribute("unidadMedidas", unidadMedidas);

        List<TipoMuestra> tipoMuestras = tipoMuestraService.listarTodos();
        model.addAttribute("tipoMuestras", tipoMuestras);
    }

    @GetMapping("/Muestra/Asociar/{muestraId}")
    public String Asociar(@PathVariable long muestraId,Model model){
        Muestra muestra = muestraService.buscarPorId(muestraId);

        Map<Long,String> itemList = new HashMap<>();
        muestra.getSolicitud().getDetalleSolicitudList().forEach(detalleSolicitud -> {
            if(!detalleSolicitud.isAsociado() && !detalleSolicitud.isEliminado()) {
                itemList.put(detalleSolicitud.getId(), detalleSolicitud.getItem().getNombre());
            }
        });

        List<ItemMuestra> itemAsociados = new ArrayList<>();

        muestra.getItemMuestraList().forEach(itemMuestra -> {
            if (!itemMuestra.isEliminado()){
                itemAsociados.add(itemMuestra);
            }
        });

        model.addAttribute("muestraId", muestraId);
        model.addAttribute("itemList", itemList);
        model.addAttribute("itemAsociados", itemAsociados);
        model.addAttribute("SolicitudCodigo", muestra.getSolicitud().getCodigoSolicitud());
        return "Muestra/Asociar";
    }
}
