package com.tallerwebi.presentacion.Datos;

public class DatosViaje {

    private Integer idViaje;
    private String lugarDeSalida;
    private String lugarDeLlegada;
    private String nombre;
    private String precio;
    private String codigoPostal;

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

    public Integer getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Integer idViaje) {
        this.idViaje = idViaje;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
