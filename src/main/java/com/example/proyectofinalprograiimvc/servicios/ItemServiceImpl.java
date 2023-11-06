package com.example.proyectofinalprograiimvc.servicios;

import com.example.proyectofinalprograiimvc.modelo.Item;
import com.example.proyectofinalprograiimvc.repositorio.ItemRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements CrudService<Item>{

    @Autowired
    private  ItemRepositorio itemRepositorio;

    @Cacheable("items")
    @Override
    public List<Item> listarTodos() {
        return itemRepositorio.findAll();
    }

    @Override
    public Item buscarPorId(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Item guardar(Item entidad) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(Item entidad) {

    }

}
