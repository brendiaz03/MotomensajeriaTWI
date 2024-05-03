package com.tallerwebi.dominio.excepcion;

public class ConductorExistenteException extends Exception {
    private final String mensajeError;

    public ConductorExistenteException(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    @Override
    public String getMessage() {
        return mensajeError;
    }

}

