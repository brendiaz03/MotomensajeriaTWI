package com.tallerwebi.services;

import com.tallerwebi.models.Cliente;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {
    void agregarCliente(Cliente clienteNuevo);
}