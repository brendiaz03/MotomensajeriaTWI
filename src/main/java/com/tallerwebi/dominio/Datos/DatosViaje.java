package com.tallerwebi.dominio.Datos;

public class DatosViaje {

    private String nombre;
    private String lugarDeSalida;
    private String lugarDeLlegada;

    public DatosViaje(String nombre, String lugarDeSalida, String lugarDeLlegada) {
        this.nombre = nombre;
        this.lugarDeSalida = lugarDeSalida;
        this.lugarDeLlegada = lugarDeLlegada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugarDeSalida() {
        return lugarDeSalida;
    }

    public void setLugarDeSalida(String lugarDeSalida) {
        this.lugarDeSalida = lugarDeSalida;
    }

    public String getLugarDeLlegada() {
        return lugarDeLlegada;
    }

    public void setLugarDeLlegada(String lugarDeLlegada) {
        this.lugarDeLlegada = lugarDeLlegada;
    }
}
