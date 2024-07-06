package com.tallerwebi.dominio.exceptions;

public class ClienteNoEncontradoException extends Exception {

    private final String mensaje;

    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }

    public String getMessage() {
        return mensaje;
    }
}