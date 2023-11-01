package com.example.proyectofinalprograiimvc.modelo;

import com.example.proyectofinalprograiimvc.repositorio.ItemMuestraRepositorio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private boolean eliminado;

    @ManyToOne
    @JoinColumn (name ="tipo_de_examen_id")
    private TipoExamen tipoExamen;

    @OneToMany (mappedBy = "item")
    private List<DetalleSolicitud> detalleSolicitudList = new ArrayList<>();

}

