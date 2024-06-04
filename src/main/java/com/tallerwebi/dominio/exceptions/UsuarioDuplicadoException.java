package com.tallerwebi.dominio.exceptions;

public class UsuarioDuplicadoException extends Exception{
    private final String mensajeError;
    public UsuarioDuplicadoException(String message) {
        this.mensajeError=message;

    }
    @Override
    public String getMessage() {
        return mensajeError;
    }
}


