package com.example.proyectofinalprograiimvc.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@Table (name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private  String nombreUsuario;
    private boolean habilitado;
    private String correo;
    private String contrasenia;
    private String tipoUsuario;
    private LocalDateTime fechaCreacion;
    @ManyToOne
    @JoinColumn (name ="rol_id" )
    private Rol rol;



}
