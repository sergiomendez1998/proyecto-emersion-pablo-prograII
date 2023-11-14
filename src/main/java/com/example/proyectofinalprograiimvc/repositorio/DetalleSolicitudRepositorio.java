package com.example.proyectofinalprograiimvc.repositorio;

import com.example.proyectofinalprograiimvc.modelo.DetalleSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleSolicitudRepositorio extends JpaRepository<DetalleSolicitud, Long> {
    public List<DetalleSolicitud> findBySolicitudId(Long id);
}
