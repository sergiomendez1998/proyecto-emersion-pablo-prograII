package com.example.proyectofinalprograiimvc.modelo;

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
@Table(name = "solicitudes")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoSolicitud;
    private String numeroSoporte;
    private String correo;
    private String observacion;
    private LocalDateTime fechaRecepcion;
    private boolean eliminado;

    @ManyToOne
    @JoinColumn (name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_tipo_soporte")
    private TipoSoporte tipoSoporte;

    @OneToMany (mappedBy = "solicitud")
    private List<DetalleSolicitud> detalleSolicitudList = new ArrayList<>();


    @OneToMany (mappedBy = "solicitud")
    private  List<EstadoSolicitud> estadoSolicitudes = new ArrayList<>();

    @OneToMany(mappedBy = "solicitud")
    private List<Muestra> muestraList = new ArrayList<>();

}
