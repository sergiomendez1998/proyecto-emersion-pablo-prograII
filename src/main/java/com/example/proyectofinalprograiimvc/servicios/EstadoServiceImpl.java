package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.Estado;
import com.example.proyectofinalprograiimvc.repositorio.EstadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EstadoServiceImpl implements CrudService<Estado>{

    @Autowired
    private EstadoRepositorio estadoRepositorio;
    @Cacheable("estados")
    @Override
    public List<Estado> listarTodos() { return estadoRepositorio.findAll(); }

    @Override
    public Estado buscarPorId(Long id) {
            return listarTodos()
                    .stream()
                    .filter(estado -> estado.getId().equals(id))
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public Estado guardar(Estado entidad) {
            throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Long id) {
            throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(Estado entidad) {

    }
}
