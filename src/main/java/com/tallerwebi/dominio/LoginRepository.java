package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.usuarios.Cliente;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;

import java.util.List;

public interface LoginRepository {

    void guardarConductor(Conductor conductor);

    Cliente buscarCliente(String email, String password);

    Conductor buscarConductor(String email, String password);

    void guardarCliente(Cliente cliente);
}