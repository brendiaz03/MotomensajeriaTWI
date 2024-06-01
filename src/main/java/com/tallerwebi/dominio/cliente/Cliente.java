package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Cliente extends Usuario {

    public Cliente() {
        super();
    }

    public Cliente(String nombre, String apellido, Integer numeroDeDni, String email, String numeroDeTelefono, String nombreUsuario, String password, String domicilio, TipoUsuario tipoUsuario) {
        super(nombre, apellido, numeroDeDni, email, numeroDeTelefono, nombreUsuario, password, domicilio, tipoUsuario);
    }
}