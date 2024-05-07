package com.tallerwebi.dominio.conductor;

public class CVUInvalidoException extends Exception{
    private final String mensajeError;

    public CVUInvalidoException(String message) {
        this.mensajeError=message;
    }

    @Override
    public String getMessage() {
        return mensajeError;
    }
}
