package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.Solicitud;
import com.example.proyectofinalprograiimvc.repositorio.SolicitudRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudServiceImpl implements CrudService<Solicitud>{

    @Autowired
    private  SolicitudRepositorio solicitudRepositorio;

    @Override
    public List<Solicitud> listarTodos() {
        return solicitudRepositorio.findAll().stream()
                .filter(solicitud -> !solicitud.isEliminado())
                .toList();
    }

    @Override
    public Solicitud buscarPorId(Long id) {
        return solicitudRepositorio.findById(id).orElse(null);
    }

    @Override
    public Solicitud guardar(Solicitud entidad) {
        return solicitudRepositorio.save(entidad);
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public void actualizar(Solicitud entidad) {
         solicitudRepositorio.save(entidad);
    }
}
