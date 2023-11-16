package com.example.proyectofinalprograiimvc.controllers;


import com.example.proyectofinalprograiimvc.dto.MuestraDTO;
import com.example.proyectofinalprograiimvc.modelo.Muestra;
import com.example.proyectofinalprograiimvc.modelo.TipoMuestra;
import com.example.proyectofinalprograiimvc.modelo.UnidadMedida;
import com.example.proyectofinalprograiimvc.servicios.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

}
