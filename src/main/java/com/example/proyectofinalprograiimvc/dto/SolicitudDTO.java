package com.example.proyectofinalprograiimvc.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SolicitudDTO {
    private String correo;
    private String numeroSoporte;
    private String observacion;
    private LocalDateTime fechaRecepcion;
    private Long clienteId;
    private Long tipoSoporteId;
    private List<ItemDTO> items = new ArrayList<>();
}
