package com.tallerwebi.presentacion.Datos;

public class DatosLoginConductor {
    private String usuario;
    private String password;

    public DatosLoginConductor(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public DatosLoginConductor() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
