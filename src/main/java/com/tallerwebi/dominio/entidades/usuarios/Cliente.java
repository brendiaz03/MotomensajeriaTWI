package com.tallerwebi.dominio.entidades.usuarios;

import com.tallerwebi.dominio.dto.ClienteDto;

import javax.persistence.Entity;

@Entity
public class Cliente extends UsuarioDos {

    public Cliente(ClienteDto clienteDto) {
        super(clienteDto.getEmail(), clienteDto.getPassword(),clienteDto.getNombre(), clienteDto.getApellido(), clienteDto.getNumeroDeDni(), clienteDto.getDomicilio(), clienteDto.getTelefono(), clienteDto.getUsuario());
    }

    public Cliente (String email, String password) {
        super(email, password);
    }
    public Cliente(){
    }
}