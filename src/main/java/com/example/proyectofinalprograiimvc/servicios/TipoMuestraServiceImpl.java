package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.TipoMuestra;
import com.example.proyectofinalprograiimvc.repositorio.TipoMuestraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoMuestraServiceImpl implements CrudService<TipoMuestra>{

    @Autowired
    private TipoMuestraRepositorio tipoMuestraRepositorio;
    @Cacheable("tipoMuestras")

    @Override
    public List<TipoMuestra> listarTodos() {
        return tipoMuestraRepositorio.findAll();
    }

    @Override
    public TipoMuestra buscarPorId(Long id) {
       return listarTodos()
               .stream()
               .filter(tipoMuestra -> tipoMuestra.getId().equals(id))
               .findFirst()
               .orElse(null);
    }

    @Override
    public TipoMuestra guardar(TipoMuestra entidad) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(TipoMuestra entidad) {

    }
}
