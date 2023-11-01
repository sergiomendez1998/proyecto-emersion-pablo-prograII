package com.example.proyectofinalprograiimvc.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "item_muestra")
public class ItemMuestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean eliminado;

    @ManyToOne
    @JoinColumn(name = "id_item")
    private DetalleSolicitud detalleSolicitud;

    @ManyToOne
    @JoinColumn (name = "id_muestra")
    private Muestra muestra;

}
