package com.tallerwebi.controllers;

import com.tallerwebi.dto.ClienteDto;
import com.tallerwebi.models.Cliente;
import com.tallerwebi.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/nuevoCliente")
    public String nuevoCliente(@RequestBody ClienteDto clienteDto) {
        Cliente nuevoCliente = crearClienteConClienteDto(clienteDto);
        this.clienteService.agregarCliente(nuevoCliente);
        return "El cliente se ha agregado correctamente";
    }

    private Cliente crearClienteConClienteDto(ClienteDto clienteDto) {
        return new Cliente(clienteDto);
    }
}