package com.example.proyectofinalprograiimvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SolicitudDTO {
    @NotEmpty (message = "El correo es requerido")
    @Email (message = "El correo es inválido")
    private String correo;
    @NotEmpty(message = "El número de soporte es requerido")
    private String numeroSoporte;
    @NotEmpty(message = "La observación es  requerida")
    private String observacion;
    private LocalDateTime fechaRecepcion;
    private String clienteCui;
    @NotNull(message = "El ID del soporte es requerido")
    private Long tipoSoporteId;
    @NotEmpty(message = "Los items son requeridos")
    private List<ItemDTO> items = new ArrayList<>();
}
