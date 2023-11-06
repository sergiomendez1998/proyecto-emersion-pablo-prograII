package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.Cliente;
import com.example.proyectofinalprograiimvc.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteServiceImpl implements CrudService<Cliente>{

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Override
    public List<Cliente> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepositorio.findById(id).orElse(null);
    }

    @Override
    public Cliente guardar(Cliente entidad) {
        System.out.println("Guardando cliente");
        clienteRepositorio.save(entidad);
        return new Cliente();
    }

    @Override
    public void eliminar(Long id) {
       throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(Cliente entidad) {

    }
}
