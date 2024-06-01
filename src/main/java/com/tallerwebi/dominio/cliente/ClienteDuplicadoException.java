package com.tallerwebi.dominio.cliente;

public class ClienteDuplicadoException extends Throwable {
    private final String mensajeError;

    public ClienteDuplicadoException(String message) {
        this.mensajeError=message;
    }

    @Override
    public String getMessage() {
        return mensajeError;
    }
}
