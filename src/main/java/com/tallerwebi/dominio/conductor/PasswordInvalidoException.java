package com.tallerwebi.dominio.conductor;

public class PasswordInvalidoException extends Exception {
    private final String mensajeError;

    public PasswordInvalidoException(String message) {
        this.mensajeError=message;
    }
    @Override
    public String getMessage() {
        return mensajeError;
    }
}
