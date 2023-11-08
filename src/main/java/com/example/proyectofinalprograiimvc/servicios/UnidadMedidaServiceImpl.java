package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.UnidadMedida;
import com.example.proyectofinalprograiimvc.repositorio.UnidadMedidaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UnidadMedidaServiceImpl implements CrudService<UnidadMedida>{
    @Autowired
    private UnidadMedidaRepositorio unidadMedidaRepositorio;

    @Cacheable ("unidadMedidas")
    @Override
    public List<UnidadMedida> listarTodos() {
        return unidadMedidaRepositorio.findAll();
    }

    @Override
    public UnidadMedida buscarPorId(Long id) {
        return listarTodos()
                .stream()
                .filter(unidadMedida -> unidadMedida.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UnidadMedida guardar(UnidadMedida entidad) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(UnidadMedida entidad) {

    }
}
