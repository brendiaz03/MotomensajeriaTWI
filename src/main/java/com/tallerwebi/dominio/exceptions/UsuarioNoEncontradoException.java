package com.tallerwebi.dominio.exceptions;

public class UsuarioNoEncontradoException extends Exception {
    private final String mensajeError;

    public UsuarioNoEncontradoException(String message) {
        this.mensajeError=message;
    }
    @Override
    public String getMessage() {
        return mensajeError;
    }
}
