package com.tallerwebi.dominio.entidades.usuarios;

import com.tallerwebi.dominio.dto.ClienteDto;

import javax.persistence.Entity;

@Entity
public class Cliente extends UsuarioDos {
    public Cliente(String email, String password, String nombre, String apellido, Integer numeroDeDni, String domicilio, Integer telefono, String usuario) {
        super(email, password, nombre, apellido, numeroDeDni, domicilio, telefono, usuario);
    }

    public Cliente (String email, String password) {
        super(email, password);
    }

    public Cliente(){
    }
}