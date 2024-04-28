package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.usuarios.Cliente;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface LoginService {

    void guardarConductor(Conductor conductor) throws UsuarioExistente;

    void guardarCliente(Cliente cliente) throws UsuarioExistente;

    Conductor consultarConductor(String email, String password);

    Cliente consultarCliente(String email, String password);
}