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
@Table(name = "detalle_solicitud")
public class DetalleSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaCreacion;
    private boolean asociado;
    private boolean eliminado;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToMany (mappedBy = "detalleSolicitud")
    private List<ItemMuestra> itemMuestraList = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        fechaCreacion = LocalDateTime.now();
    }
}
