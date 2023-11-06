package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.Rol;
import com.example.proyectofinalprograiimvc.repositorio.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements CrudService<Rol>{

    @Autowired
    private RolRepositorio rolRepositorio;

    @Cacheable("roles")
    @Override
    public List<Rol> listarTodos() {
        return rolRepositorio.findAll();
    }

    @Override
    public Rol buscarPorId(Long id) {
        return rolRepositorio.findById(id).orElse(null);
    }

    @Override
    public Rol guardar(Rol entidad) {
       throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Long id) {
       throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(Rol entidad) {

    }
}
