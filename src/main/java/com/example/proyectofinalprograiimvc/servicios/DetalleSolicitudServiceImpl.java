package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.DetalleSolicitud;
import com.example.proyectofinalprograiimvc.repositorio.DetalleSolicitudRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleSolicitudServiceImpl implements  CrudService<DetalleSolicitud>{

    @Autowired
    private DetalleSolicitudRepositorio detalleSolicitudRepositorio;

    @Override
    public List<DetalleSolicitud> listarTodos() {
        return detalleSolicitudRepositorio.findAll();
    }

    @Override
    public DetalleSolicitud buscarPorId(Long id) {
        return detalleSolicitudRepositorio.findById(id).orElse(null);
    }

    @Override
    public DetalleSolicitud guardar(DetalleSolicitud entidad) {
        return detalleSolicitudRepositorio.save(entidad);
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public void actualizar(DetalleSolicitud entidad) {

    }

    public List<DetalleSolicitud> buscarPorSolicitudId(Long id){
        return detalleSolicitudRepositorio.findBySolicitudId(id)
                .stream()
                .filter(detalleSolicitud -> !detalleSolicitud.isEliminado() || !detalleSolicitud.isAsociado())
                .toList();
    }
}
