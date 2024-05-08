package com.tallerwebi.dominio.conductor;

public class EmailInvalidoException extends Exception {
    private final String mensajeError;
    public EmailInvalidoException(String message) {
        this.mensajeError=message;
    }

    @Override
    public String getMessage() {
        return mensajeError;
    }
}