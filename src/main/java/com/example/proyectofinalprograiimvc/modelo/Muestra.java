package com.example.proyectofinalprograiimvc.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "muestras")
public class Muestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String etiqueta;
    private String presentacion;
    private int cantidad;
    private LocalDateTime fechaCreacion;
    private Date fechaVencimiento;
    private boolean eliminado;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn (name = "id_tipo_de_muestra")
    private TipoMuestra tipoMuestra;

    @ManyToOne
    @JoinColumn(name = "id_unidad_de_medida")
    private UnidadMedida unidadMedida;

    @OneToMany(mappedBy ="muestra")
    private List<ItemMuestra> itemMuestraList = new ArrayList<>();




}



