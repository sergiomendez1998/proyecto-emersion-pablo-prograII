package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.Muestra;
import com.example.proyectofinalprograiimvc.repositorio.MuestraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuestraServiceImpl implements CrudService<Muestra> {

    @Autowired
    private MuestraRepositorio muestraRepositorio;

    @Override
    public List<Muestra> listarTodos() {
        return muestraRepositorio.findAll().stream()
                .filter(muestra -> !muestra.isEliminado())
                .toList();
    }

    @Override
    public Muestra buscarPorId(Long id) {return muestraRepositorio.findById(id).orElse(null);
    }

    @Override
    public Muestra guardar(Muestra entidad) {
        return muestraRepositorio.save(entidad);
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public void actualizar(Muestra entidad) { muestraRepositorio.save(entidad);    }
}
