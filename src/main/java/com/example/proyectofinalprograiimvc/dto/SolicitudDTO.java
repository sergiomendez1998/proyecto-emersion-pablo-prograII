package com.example.proyectofinalprograiimvc.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SolicitudDTO {
    @NotNull(message = "El correo es requerido")
    @NotEmpty (message = "El correo es requerido")
    @Email (message = "El correo es inválido")
    private String correo;

    @NotNull(message = "El número de soporte es requerido")
    @NotEmpty(message = "El número de soporte es requerido")
    @Pattern(regexp = "^[0-9]{8}$", message = "El número de soporte debe contener exactamente 8 números")
    private String numeroSoporte;

    @NotEmpty(message = "La observación es  requerida")
    @NotNull(message = "La observación es requerida")
    @Size(min = 25, message = "La observación debe tener al menos 25 caracteres")
    @Size(max = 250, message = "La observación debe tener máximo 250 caracteres")
    private String observacion;

    private LocalDateTime fechaRecepcion;

    private String clienteCui;

    @NotNull(message = "El ID del soporte es requerido")
    @Min(value = 1, message = "El ID del soporte debe ser mayor que 0")
    private Long tipoSoporteId;

    @NotEmpty(message = "La lista de examenes no puede ser vacia")
    @NotNull(message = "La lista de examenes no puede ser vacia")
    private List<ItemDTO> items = new ArrayList<>();
}
