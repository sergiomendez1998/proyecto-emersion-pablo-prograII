package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.Usuario;
import com.example.proyectofinalprograiimvc.repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements CrudService<Usuario>{

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepositorio){
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return null;
    }

    @Override
    public Usuario guardar(Usuario entidad) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public boolean validarParaGuardar(Usuario entidad) {
        return false;
    }

    @Override
    public boolean validarParaEliminar(Usuario entidad) {
        return false;
    }

    @Override
    public boolean validarParaActualizar(Usuario entidad) {
        return false;
    }
    public Usuario buscarPorCorreo(String correo){
        return usuarioRepositorio.findByCorreo(correo);
    }
}
