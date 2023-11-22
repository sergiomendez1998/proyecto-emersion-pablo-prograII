package com.example.proyectofinalprograiimvc.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MuestraDTO {
    @NotEmpty(message = "La presentacion es requerida")
    @Size(min = 5, message = "La presentacion debe tener al menos 5 caracteres")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "La presentacion debe contener solo letras y espacios")
    private String presentacion;

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser mayor que 0")
    private int cantidad;

    @NotNull(message = "El tipo de muestra es requerido")
    @Min(value = 1, message = "El tipo de muestra es requerido")
    private Long idTipoMuestra;

    @NotNull(message = "La unidad de medida es requerida")
    @Min(value = 1, message = "La unidad de medida es requerida")
    private Long idUnidadMedida;

    @NotNull(message = "La solicitud es requerida")
    @Min(value = 1, message = "La solicitud es requerida")
    private Long idSolicitud;

    @NotNull(message = "Es obligatorio fecha de vencimiento")
    @NotEmpty(message = "La fecha de vencimiento es requerida")
    private String fechaVencimiento;
}
