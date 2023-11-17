package com.example.proyectofinalprograiimvc.controllers;


import com.example.proyectofinalprograiimvc.dto.MuestraDTO;
import com.example.proyectofinalprograiimvc.modelo.*;
import com.example.proyectofinalprograiimvc.repositorio.ItemMuestraRepositorio;
import com.example.proyectofinalprograiimvc.servicios.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @PostMapping("/guardarMuestra")
    public String guardarMuestra(@Valid MuestraDTO muestraDTO, BindingResult bindingResult, RedirectAttributes redirect){

        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        Muestra nuevaMuestra = new Muestra();

        nuevaMuestra.setPresentacion(muestraDTO.getPresentacion());
        nuevaMuestra.setCantidad(muestraDTO.getCantidad());
        TipoMuestra tipoMuestra = tipoMuestraService.buscarPorId(muestraDTO.getIdTipoMuestra());
        nuevaMuestra.setTipoMuestra(tipoMuestra);
        UnidadMedida unidadMedida = unidadMedidaService.buscarPorId(muestraDTO.getIdUnidadMedida());
        nuevaMuestra.setUnidadMedida(unidadMedida);
        nuevaMuestra.setFechaVencimiento(muestraDTO.getFechaVencimiento());
        nuevaMuestra.setSolicitud(solicitudService.buscarPorId(muestraDTO.getSolicidudId()));

        muestraService.guardar(nuevaMuestra);

        return "redirect:/";
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

}
