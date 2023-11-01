package com.example.proyectofinalprograiimvc.repositorio;

import com.example.proyectofinalprograiimvc.modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Long> {
}
