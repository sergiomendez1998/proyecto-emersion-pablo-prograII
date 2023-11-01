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
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cui;
    private String primerNombre;
    private String apellido;
    private String direccion;
    private String numeroTelefono;
    private String genero;

    @OneToOne
    @JoinColumn (name = "usuario_id")
    private Usuario usuario;

}
