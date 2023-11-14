package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.TipoSoporte;
import com.example.proyectofinalprograiimvc.repositorio.TipoSoporteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoSoporteServiceImpl implements CrudService<TipoSoporte>{

    @Autowired
    private TipoSoporteRepositorio tipoSoporteRepositorio;

    @Override
    @Cacheable("tipoSoportes")
    public List<TipoSoporte> listarTodos() {
        return tipoSoporteRepositorio.findAll();
    }

    @Override
    public TipoSoporte buscarPorId(Long id) {
      return listarTodos()
              .stream()
              .filter(tipoSoporte -> tipoSoporte.getId().equals(id))
              .findFirst()
              .orElse(null);
    }

    @Override
    public TipoSoporte guardar(TipoSoporte entidad) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(TipoSoporte entidad) {
     throw new UnsupportedOperationException("Not supported yet.");
    }
}
