package com.example.proyectofinalprograiimvc.servicios;

import java.util.List;

public interface CrudService<T> {

    List<T> listarTodos();
    T buscarPorId(Long id);
    T guardar(T entidad);
    void eliminar(Long id);
    boolean validarParaGuardar(T entidad);
    boolean validarParaEliminar(T entidad);
    boolean validarParaActualizar(T entidad);
}
