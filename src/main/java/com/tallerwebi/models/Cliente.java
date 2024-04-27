package com.tallerwebi.models;

import com.tallerwebi.dominio.UsuarioDos;
import com.tallerwebi.dto.ClienteDto;

import javax.persistence.Entity;

@Entity
public class Cliente extends UsuarioDos {

    public Cliente(ClienteDto clienteDto) {
        super(clienteDto.getEmail(), clienteDto.getPassword(),clienteDto.getNombre(), clienteDto.getApellido(), clienteDto.getNumeroDeDni(), clienteDto.getDomicilio(), clienteDto.getTelefono(), clienteDto.getUsuario());
    }

    public Cliente(){
    }
}