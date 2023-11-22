package com.example.proyectofinalprograiimvc.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MuestraDTO {
    @NotEmpty(message = "La presentacion es requerida")
    private String presentacion;
    @NotEmpty(message = "La cantidad es requerida")
    private int cantidad;
    @NotEmpty(message = "Es obligatorio el tipo de muestra")
    private Long idTipoMuestra;
    @NotEmpty(message = "Es obligatorio la unidad de medida")
    private Long idUnidadMedida;
    @NotEmpty(message = "Es obligatorio fecha de vencimiento")
    private Date fechaVencimiento;
}
