package com.example.proyectofinalprograiimvc.controllers;


import com.example.proyectofinalprograiimvc.Utils;
import com.example.proyectofinalprograiimvc.dto.MuestraDTO;
import com.example.proyectofinalprograiimvc.modelo.*;
import com.example.proyectofinalprograiimvc.repositorio.ItemMuestraRepositorio;
import com.example.proyectofinalprograiimvc.servicios.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/Muestra/Create/{solicitudId}")
    public String MuestraIn(@PathVariable Long solicitudId, MuestraDTO muestraDTO, Model model){


        model.addAttribute("solicitudId", solicitudId);
        return "Muestra/Crear";
    }

    @PostMapping("/guardarMuestra/{solicitudId}")
    public String guardarMuestra(@PathVariable Long solicitudId, @Valid MuestraDTO muestraDTO, BindingResult bindingResult, RedirectAttributes redirect){

        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        Muestra nuevaMuestra = new Muestra();

        nuevaMuestra.setCodigoMuestra(Utils.generarNumeroMuestraRandom());
        nuevaMuestra.setPresentacion(muestraDTO.getPresentacion());
        nuevaMuestra.setCantidad(muestraDTO.getCantidad());
        TipoMuestra tipoMuestra = tipoMuestraService.buscarPorId(muestraDTO.getIdTipoMuestra());
        nuevaMuestra.setTipoMuestra(tipoMuestra);
        UnidadMedida unidadMedida = unidadMedidaService.buscarPorId(muestraDTO.getIdUnidadMedida());
        nuevaMuestra.setUnidadMedida(unidadMedida);
        nuevaMuestra.setFechaVencimiento(muestraDTO.getFechaVencimiento());
        nuevaMuestra.setSolicitud(solicitudService.buscarPorId(solicitudId));

        muestraService.guardar(nuevaMuestra);
         redirect.addFlashAttribute("mensaje", "Muestra creada exitosamente!");
        return "redirect:/Solicitud";
    }

    @DeleteMapping("/eliminarMuestra/{id}")
    public String eliminarMuestra(@PathVariable Long id, RedirectAttributes redirect){
           Muestra muestra = muestraService.buscarPorId(id);
           muestra.setEliminado(true);

           muestra.getItemMuestraList().forEach(itemMuestra -> {
               itemMuestra.setEliminado(true);
               itemMuestra.getDetalleSolicitud().setAsociado(false);
           });

           muestraService.guardar(muestra);
           redirect.addFlashAttribute("mensaje", "Muestra eliminada correctamente");
          return "redirect:/";
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
        return "redirect:/";
    }

    @PutMapping("/desasociarItemMuestra/{muestraId}")
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
        return "redirect:/";
    }

    @GetMapping ("/informacionGeneralMuestra/{muestraId}")
    public String informacionGeneralMuestra(@PathVariable Long muestraId, Model model){

        Muestra muestra = muestraService.buscarPorId(muestraId);

        Map<String, String> informacionGeneralMuestra = new HashMap<>();
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

        model.addAttribute("informacionGeneralMuestra", muestra);
        return "redirect:/";
    }

}
