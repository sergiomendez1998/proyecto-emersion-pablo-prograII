package com.example.proyectofinalprograiimvc.repositorio;

import com.example.proyectofinalprograiimvc.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
}
