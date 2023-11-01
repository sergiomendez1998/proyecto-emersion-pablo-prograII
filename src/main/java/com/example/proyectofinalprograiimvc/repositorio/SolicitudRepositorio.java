package com.example.proyectofinalprograiimvc.repositorio;

import com.example.proyectofinalprograiimvc.modelo.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepositorio extends JpaRepository<Solicitud, Long> {
}
