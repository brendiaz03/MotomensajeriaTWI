package com.tallerwebi.presentacion.Datos;

public class DatosLogin {
    private String usuario;
    private String password;

    public DatosLogin(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public DatosLogin() {
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
