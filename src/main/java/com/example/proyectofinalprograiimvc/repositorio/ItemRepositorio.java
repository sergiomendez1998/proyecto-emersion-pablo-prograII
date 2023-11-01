package com.example.proyectofinalprograiimvc.repositorio;

import com.example.proyectofinalprograiimvc.modelo.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositorio extends JpaRepository<Item, Long> {
}
