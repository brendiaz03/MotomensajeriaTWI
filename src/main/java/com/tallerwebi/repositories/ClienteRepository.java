package com.tallerwebi.repositories;

import com.tallerwebi.models.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository {
    void save(Cliente clienteNuevo);
}