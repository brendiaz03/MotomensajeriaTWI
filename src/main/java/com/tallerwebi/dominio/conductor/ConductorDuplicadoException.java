package com.tallerwebi.dominio.conductor;

public class ConductorDuplicadoException extends Exception{
    private final String mensajeError;
    public ConductorDuplicadoException(String message) {
        this.mensajeError=message;

    }
    @Override
    public String getMessage() {
        return mensajeError;
    }
}


