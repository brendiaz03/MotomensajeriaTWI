package com.tallerwebi.dominio.conductor;

public class ConductorNoEncontradoException extends Exception {
    private final String mensajeError;

    public ConductorNoEncontradoException(String message) {
        this.mensajeError=message;
    }
    @Override
    public String getMessage() {
        return mensajeError;
    }
}
