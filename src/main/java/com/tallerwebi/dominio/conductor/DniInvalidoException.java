package com.tallerwebi.dominio.conductor;

public class DniInvalidoException extends Exception {
    private final String mensajeError;

    public DniInvalidoException(String message) {
        this.mensajeError=message;
    }

    @Override
    public String getMessage() {
        return mensajeError;
    }
}
