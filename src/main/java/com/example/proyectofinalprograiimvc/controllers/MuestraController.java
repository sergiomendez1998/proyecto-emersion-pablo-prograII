package com.example.proyectofinalprograiimvc.controllers;


import com.example.proyectofinalprograiimvc.dto.MuestraDTO;
import com.example.proyectofinalprograiimvc.dto.SolicitudDTO;
import com.example.proyectofinalprograiimvc.modelo.Muestra;
import com.example.proyectofinalprograiimvc.modelo.TipoMuestra;
import com.example.proyectofinalprograiimvc.modelo.UnidadMedida;
import com.example.proyectofinalprograiimvc.servicios.*;
import com.example.proyectofinalprograiimvc.modelo.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private UsuarioServiceImpl usuarioService;


    @PostMapping("/guardarMuestra")
    public String guardarMuestra(@Valid MuestraDTO muestraDTO, BindingResult bindingResult, @AuthenticationPrincipal User user, RedirectAttributes redirect){

        Usuario usuarioLogueado = usuarioService.buscarPorCorreo(user.getUsername());

        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        Muestra nuevaMuestra = new Muestra();

        nuevaMuestra.setPresentacion(muestraDTO.getPresentacion());
        nuevaMuestra.setCantidad(muestraDTO.getCantidad());
        TipoMuestra tipoMuestra = tipoMuestraService.buscarPorId(muestraDTO.getId_tipo_muestra());
        nuevaMuestra.setTipoMuestra(tipoMuestra);
        UnidadMedida unidadMedida = unidadMedidaService.buscarPorId(muestraDTO.getId_unidad_medida());
        nuevaMuestra.setUnidadMedida(unidadMedida);
        nuevaMuestra.setFechaVencimiento(muestraDTO.getFecha_vencimiento());
        nuevaMuestra.setSolicitud(solicitudService.buscarPorId(muestraDTO.getSolicidud_id()));

        muestraService.guardar(nuevaMuestra);

        return "redirect:/";
    }

}
