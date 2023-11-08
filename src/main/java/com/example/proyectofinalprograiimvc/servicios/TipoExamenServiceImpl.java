package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.TipoExamen;
import com.example.proyectofinalprograiimvc.repositorio.TipoExamenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TipoExamenServiceImpl implements CrudService<TipoExamen>{

    @Autowired
    private TipoExamenRepositorio tipoExamenRepositorio;
    @Override
    @Cacheable("tipoExamenes")
    public List<TipoExamen> listarTodos() {return tipoExamenRepositorio.findAll();
    }
    @Override
    public TipoExamen buscarPorId(Long id) {
            return listarTodos()
                    .stream()
                    .filter(tipoExamen -> tipoExamen.getId().equals(id))
                    .findFirst()
                    .orElse(null);
    }
    @Override
    public TipoExamen guardar(TipoExamen entidad) {
            throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void eliminar(Long id) {
            throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void actualizar(TipoExamen entidad) {
    }
}
